package com.nineya.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NIOClientMain {
    public static void main(String[] args) {
        InetSocketAddress remote = new InetSocketAddress("127.0.0.1", 8080);
        SocketChannel channel = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            channel = SocketChannel.open();
            channel.connect(remote);
            Scanner reader = new Scanner(System.in);
            while (true) {
                // 读取数据
                int readLength = channel.read(buffer);
                if (readLength == -1) {
                    break;
                }
                buffer.flip();
                byte[] datas = new byte[buffer.remaining()];

                buffer.get(datas);
                System.out.println("server:" + new String(datas, UTF_8));
                buffer.clear();
                System.out.print(" >");

                // 写数据
                String line = reader.nextLine();
                if (line.equals("exit")) {
                    break;
                }
                buffer.put(line.getBytes("UTF-8"));
                buffer.flip();
                channel.write(buffer);
                buffer.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
