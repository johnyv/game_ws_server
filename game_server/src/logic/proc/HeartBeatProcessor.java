package logic.proc;

import logic.protocol.HeartBeat;
import abstracted.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packet.ProtoMsg;
import session.Session;

public class HeartBeatProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(HeartBeatProcessor.class);
    HeartBeat hb;

    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {
        hb = HeartBeat.parseFrom(protoMsg.getData());
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);

//        logger.info("SystemCurrtime->" + hb.getSystemCurrtime());

        HeartBeat.Builder hbr = HeartBeat.newBuilder();
        hbr.setSystemCurrtime(System.currentTimeMillis());

        byte[] bytes = ProtoMsg.pack(1001, hbr.build());
        session.write(bytes);
    }
}
