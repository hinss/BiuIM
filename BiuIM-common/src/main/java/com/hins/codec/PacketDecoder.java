package com.hins.codec;

import com.hins.protocol.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description: 将一个完整的数据包Packet解码(也就是从字节到数据包的过程)
 * @Author:Wyman
 * @Date:2019/11/12
 */
public class PacketDecoder extends ByteToMessageDecoder {



    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        out.add(PacketCodeC.INSTANCE.decode(in));

    }
}
