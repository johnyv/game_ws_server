package processor;

import group.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processor.data.RecvPacket;

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

    public void dispatch(final Client info, final RecvPacket packet) {
        int code = packet.getCode();
        final Processor proc = get(code);
        if (proc == null) {
            return;
        }

        Runnable exec = new Runnable() {
            @Override
            public void run() {
                process(proc, info, packet);
            }
        };
        info.addQuest(exec);
    }

    private void process(final Processor proc, final Client info, final RecvPacket packet) {
        try {
            proc.process(info, packet);
        } catch (Exception e) {
        }
    }
}
