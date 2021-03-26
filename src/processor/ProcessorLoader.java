package processor;

import server.loader.JRuntimeLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class ProcessorLoader {
    private volatile JRuntimeLoader jRuntimeLoader;
    String procFilePath = "resources";
    String procFile = "processors.properties";

    public ProcessorLoader() {
        jRuntimeLoader = new JRuntimeLoader();
    }

    public void load() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = jRuntimeLoader.getClassLoader().getResourceAsStream(procFile);
        if (inputStream == null) {
            inputStream = new FileInputStream(procFilePath+"/"+procFile);
        }
        properties.load(inputStream);
        Set<Entry<Object, Object>> es = properties.entrySet();
        Iterator<Entry<Object, Object>> it = es.iterator();
        while (it.hasNext()) {
            Entry<Object, Object> entry = it.next();
            String code = (String) entry.getKey();
            String proc = (String) entry.getValue();
            System.out.println(code);
            System.out.println(proc);
            try {
                Class<?> cls;
                cls = jRuntimeLoader.getClassLoader().loadClass(proc);
                Object procObj = cls.newInstance();
            } catch (Exception e) {

            }
        }
    }
}
