package processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.loader.JRuntimeLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ProcessorLoader {
    private static Logger logger = LoggerFactory.getLogger(ProcessorLoader.class);

    private volatile JRuntimeLoader jRuntimeLoader;
    private Dispatcher dispatcher;
//    String procFilePath = "resources";
    String procFile = "resources/processors.properties";

    public ProcessorLoader(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        jRuntimeLoader = new JRuntimeLoader();
    }

    public void load() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = jRuntimeLoader.getClassLoader().getResourceAsStream(procFile);
        if (inputStream == null) {
//            throw new IOException("load file failed.");
            logger.info("try again");
            inputStream = new FileInputStream(procFile);
        }
        properties.load(inputStream);
        Set<Entry<Object, Object>> es = properties.entrySet();
        Iterator<Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String procCode = (String) entry.getKey();
            String procClass = (String) entry.getValue();
            logger.info(procCode);
            logger.info(procClass);
            try {
                int code = Integer.valueOf(procCode);
                Class<?> cls;
                cls = jRuntimeLoader.getClassLoader().loadClass(procClass);
                Object procObj = cls.newInstance();
                Processor proc = Processor.class.cast(procObj);
                proc.setCode(code);
                dispatcher.add(code,proc);
                logger.info(procObj.toString());
            } catch (Exception e) {

            }
        }
    }
}
