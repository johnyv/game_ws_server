package logic.impl;

import logic.protocol.HeartBeat;
import service.processor.Processor;
import service.processor.packet.RecvPacket;
import service.processor.packet.SendPacket;
import session.client.Client;

public class HeartBeatProcessor extends Processor {
    HeartBeat hb;
    @Override
    protected void init(Client client, RecvPacket packet) throws Exception {
        hb = HeartBeat.parseFrom(packet.getData());
    }

    @Override
    public void process(Client client, RecvPacket packet) throws Exception {
        init(client, packet);
        System.out.println(hb.getSystemCurrtime());

        HeartBeat.Builder hbr = HeartBeat.newBuilder();
        hbr.setSystemCurrtime(System.currentTimeMillis());

        SendPacket p = new SendPacket(1002, hbr.build());
        client.write(p);
    }
}
