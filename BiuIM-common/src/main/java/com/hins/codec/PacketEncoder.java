package com.hins.codec;

import com.hins.protocol.Packet;
import com.hins.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {

        PacketCodeC.INSTANCE.encode(out,msg);

    }
}
