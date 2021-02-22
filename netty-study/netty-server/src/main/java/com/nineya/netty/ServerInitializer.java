package com.nineya.netty;

import com.nineya.netty.handler.NettyServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // read 时
        pipeline.addLast("decoder", new StringDecoder());

        // send 时
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new NettyServerHandler());
    }
}
