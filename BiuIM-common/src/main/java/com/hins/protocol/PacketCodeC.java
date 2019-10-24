package com.hins.protocol;

import com.hins.protocol.request.LoginRequestPacket;
import com.hins.protocol.request.MessageRequestPacket;
import com.hins.protocol.response.LoginResponsePacket;
import com.hins.protocol.response.MessageResponsePacket;
import com.hins.serialize.Serializer;
import com.hins.serialize.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.hins.protocol.command.Command.*;

/**
 * @Description: 数据包编/解码工
 *  根据协议来编/解码数据包
 * @Author:Wyman
 * @Date:2019
 */
public class PacketCodeC {

    /** 魔数 */
    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    /** 指令与数据包关系map */
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;

    /** 算法与序列化对象对应关系map */
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    /**
     * 按照[协议]编码数据包过程
     * @param byteBuf
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象
//        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. 序列化 java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    /**
     * 按照[协议]解码数据包过程
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    /**
     * 根据算法值获取序列化工具对象
     * @param serializeAlgorithm
     * @return
     */
    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    /**
     * 根据指令获取请求数据包类
     * @param command
     * @return
     */
    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }
}
