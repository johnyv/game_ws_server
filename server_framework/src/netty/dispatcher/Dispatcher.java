package netty.dispatcher;

import netty.abstracted.Processor;
import netty.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import netty.packet.ProtoMsg;

import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    protected final static Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private static Dispatcher instance = new Dispatcher();

    Map<Integer, Processor> processors = null;

    public static Dispatcher getInstance() { return instance; }

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

    public void dispatch(final Session session, final ProtoMsg packet) {
        int code = packet.getCode();
        final Processor proc = get(code);
        if (proc == null) {
            return;
        }

        Runnable exec = new Runnable() {
            @Override
            public void run() {
                process(proc, session, packet);
            }
        };
        session.addQuest(exec);
    }

    private void process(final Processor proc, final Session session, final ProtoMsg packet) {
        try {
            proc.process(session, packet);
        } catch (Exception e) {
        }
    }
}
