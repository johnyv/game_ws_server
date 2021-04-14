package bootstrap.server.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bootstrap.server.dispatcher.Dispatcher;
import service.processor.Processor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ProcessorLoader {
    private static Logger logger = LoggerFactory.getLogger(ProcessorLoader.class);

    private volatile ClassLoader loader;
    private Dispatcher dispatcher;

    String procFile = "resources/processors.properties";

    public ProcessorLoader(Dispatcher dispatcher) {
        loader = getClass().getClassLoader();
        this.dispatcher = dispatcher;
    }

    public void load() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = loader.getResourceAsStream(procFile);
        if (inputStream == null) {
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
                cls = loader.loadClass(procClass);
                Object procObj = cls.newInstance();
                Processor proc = Processor.class.cast(procObj);
                proc.setCode(code);
                dispatcher.add(code, proc);
                logger.info(procObj.toString());
            } catch (Exception e) {

            }
        }
    }
}
