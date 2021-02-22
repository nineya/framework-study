package com.nineya.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /*
     * ChannelInboundHandlerAdapter：ChannelInboundHandlerAdapter是ChannelInboundHandler的一个简单实现，默认情况下不会做任何处理，
     *   只是简单的将操作通过fire*方法传递到ChannelPipeline中的下一个ChannelHandler中让链中的下一个ChannelHandler去处理。
     *
     * SimpleChannelInboundHandler：SimpleChannelInboundHandler支持泛型的消息处理，默认情况下消息处理完将会被自动释放，无法提供
     *   fire*方法传递给ChannelPipeline中的下一个ChannelHandler,如果想要传递给下一个ChannelHandler需要调用ReferenceCountUtil#retain方法。
     * */

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server > "+msg.toString());
        System.out.print("> ");
        //把消息往下一个Handler传
        ctx.fireChannelRead(msg);
    }
}
