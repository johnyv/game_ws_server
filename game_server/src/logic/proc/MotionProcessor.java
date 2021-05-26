package logic.proc;

import abstracted.Processor;
import com.google.protobuf.InvalidProtocolBufferException;
import logic.protocol.Motion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import packet.ProtoMsg;
import session.Session;

public class MotionProcessor extends Processor {
    private static Logger logger = LoggerFactory.getLogger(MotionProcessor.class);
    Motion motion;

    @Override
    protected void init(Session session, ProtoMsg protoMsg) throws Exception {
        try {
            motion = Motion.parseFrom(protoMsg.getData());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(Session session, ProtoMsg protoMsg) throws Exception {
        init(session, protoMsg);

        logger.info("move x:"+motion.getX());
        logger.info("move y:"+motion.getY());
    }
}
