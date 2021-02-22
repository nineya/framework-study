package com.nineya.netty;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClientMain {
    private static final int port = 8080;
    private static final String ip = "127.0.0.1";

    public void start() {
        //1.定义服务类
        Bootstrap bootstrap = new Bootstrap();
        //2.定义执行线程组
        EventLoopGroup worker = new NioEventLoopGroup();
        //3.设置线程池
        bootstrap.group(worker);
        //4.设置通道
        bootstrap.channel(NioSocketChannel.class);
        //5.添加Handler
        bootstrap.handler(new ClientInitializer());
        //6.建立连接
        ChannelFuture channelFuture = bootstrap.connect(ip, port);
        try {
            Scanner in = new Scanner(System.in);
            while (in.hasNext()) {
                String msg = in.next();
                channelFuture.channel().writeAndFlush(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyClientMain().start();
    }
}
