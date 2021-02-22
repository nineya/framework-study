package com.nineya.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerMain {
    private static final int port = 8080;
    private static final String ip = "127.0.0.1";
    /**
     * 用于分配处理业务线程的线程组个数
     */
    protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2;
    protected static final int BIZTHREADSIZE = 4;
    /*
     * NioEventLoopGroup实际上就是个线程池,
     * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件,
     * 每一个NioEventLoop负责处理m个Channel,
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel
     */
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

    public void start() {
        System.out.println("Init port: " + port);
        // 引导辅助程序
        ServerBootstrap b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ServerInitializer());
            b.option(ChannelOption.SO_BACKLOG, 128);
            b.childOption(ChannelOption.SO_KEEPALIVE, true);

            // 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定
            ChannelFuture f = b.bind(ip, port).sync();
            System.out.println("Start success: " + f.channel().localAddress());
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shutdown();
        }

    }

    protected void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) {
        new NettyServerMain().start();
    }
}
