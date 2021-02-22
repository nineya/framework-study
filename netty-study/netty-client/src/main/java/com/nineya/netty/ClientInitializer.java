package com.nineya.netty;

import com.nineya.netty.handler.NettyClientHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ClientInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        System.out.println("client channel init!");
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("StringDecoder",new StringDecoder());
        pipeline.addLast("StringEncoder",new StringEncoder());
        pipeline.addLast("ClientHandler",new NettyClientHandler());
    }
}
