package logic.proc;

import abstracted.Processor;
import logic.protocol.HBInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packet.ProtoMsg;
import session.Session;

public class HeartBeatProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(HeartBeatProcessor.class);
    HBInfo hb;

    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {
        hb = HBInfo.parseFrom(protoMsg.getData());
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);

//        logger.info("SystemCurrtime->" + hb.getSystemCurrtime());

        HBInfo.Builder hbr = HBInfo.newBuilder();
        hbr.setSystemCurrtime(System.currentTimeMillis());

        byte[] bytes = ProtoMsg.pack(1001, hbr.build());
        session.write(bytes);
    }
}
