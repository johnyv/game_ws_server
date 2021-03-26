package processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    protected final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    Map<Integer, Processor> processors = null;

    public Dispatcher() {
        processors = new HashMap<Integer, Processor>();
    }

    public void add(Integer code, Processor proc) {
        processors.put(code, proc);
    }

    public Processor get(Integer code) {
        Processor proc = processors.get(code);
        if (proc == null) {
            logger.warn("Invalid code:" + code);
        }
        return proc;
    }

    public void dispatch(){

    }

    private void process(){

    }
}
