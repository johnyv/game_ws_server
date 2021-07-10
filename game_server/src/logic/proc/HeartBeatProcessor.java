package logic.proc;

import netty.abstracted.Processor;
import logic.protocol.HBInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import websocket.protobuf.ProtobufMsg;
import netty.session.Session;

public class HeartBeatProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(HeartBeatProcessor.class);
    HBInfo hb;

    @Override
    protected void init(Session session, ProtobufMsg protobufMsg) throws Exception {
        hb = HBInfo.parseFrom(protobufMsg.getData());
    }

    @Override
    public void process(Session session, ProtobufMsg protobufMsg) throws Exception {
        init(session, protobufMsg);

//        logger.info("SystemCurrtime->" + hb.getSystemCurrtime());

        HBInfo.Builder hbr = HBInfo.newBuilder();
        hbr.setSystemCurrtime(System.currentTimeMillis());

        byte[] bytes = ProtobufMsg.pack(1001, hbr.build());
        session.write(bytes);
    }
}
