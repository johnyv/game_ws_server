package logic.proc;

import logic.protocol.HeartBeat;
import abstracted.Processor;
import packet.MsgPack;
import packet.SendPacket;
import session.Session;

public class HeartBeatProcessor extends Processor {
    HeartBeat hb;
    @Override
    protected void init(Session session, MsgPack packet) throws Exception {
        hb = HeartBeat.parseFrom(packet.getData());
    }

    @Override
    public void process(Session session, MsgPack packet) throws Exception {
        System.out.println("HeartBeatProcessor process");
        init(session, packet);
        System.out.println(hb.getSystemCurrtime());

        HeartBeat.Builder hbr = HeartBeat.newBuilder();
        hbr.setSystemCurrtime(System.currentTimeMillis());

        SendPacket p = new SendPacket(1001, hbr.build());
        session.write(p);
    }
}
