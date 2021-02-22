package com.nineya.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import static java.nio.charset.StandardCharsets.UTF_8;

public class NIOServerMain implements Runnable {

    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private int num = 0;

    public static void main(String[] args) {
        new Thread(new NIOServerMain(8080)).start();
    }

    public NIOServerMain(int port) {
        init(port);
    }

    private void init(int port) {
        try {
            System.out.println("start...");
            this.selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.selector.select();
                Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();
                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {
                                accept(key);
                            }
                        } catch (CancelledKeyException e) {
                            key.cancel();
                        }
                        try {
                            if (key.isReadable()) {
                                read(key);
                            }
                        } catch (CancelledKeyException e) {
                            key.cancel();
                        }
                        try {
                            if (key.isWritable()) {
                                write(key);
                            }
                        } catch (CancelledKeyException e) {
                            key.cancel();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void write(SelectionKey key) {
        this.writeBuffer.clear();
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            String message = String.format("已收到%d条消息！", ++num);
            System.out.println("server > " + message);
            writeBuffer.put(message.getBytes(UTF_8));
            writeBuffer.flip();
            channel.write(writeBuffer);
            channel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read(SelectionKey key) {
        try {
            this.readBuffer.clear();
            SocketChannel channel = (SocketChannel) key.channel();
            int readLength = channel.read(readBuffer);
            if (readLength == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            this.readBuffer.flip();
            byte[] datas = new byte[readBuffer.remaining()];
            readBuffer.get(datas);
            System.out.println("client: " + new String(datas, UTF_8));
            channel.register(this.selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = serverSocketChannel.accept();
            channel.configureBlocking(false);
            channel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
