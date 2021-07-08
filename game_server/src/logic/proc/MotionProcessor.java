package logic.proc;

import netty.abstracted.Processor;
import com.google.protobuf.InvalidProtocolBufferException;
import logic.protocol.MotionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import netty.packet.ProtoMsg;
import netty.session.Session;
import user.UserManager;

public class MotionProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(MotionProcessor.class);
    MotionInfo motion;

    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {
        try {
            motion = MotionInfo.parseFrom(protoMsg.getData());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);

        logger.info("move id:" + motion.getUid());
        logger.info("move x:" + motion.getX());
        logger.info("move y:" + motion.getY());
        byte[] bytes = ProtoMsg.pack(2003, motion);
        UserManager.getInstance().sendToAll(bytes);
    }
}
