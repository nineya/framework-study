package com.nineya.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress() +"加入");
        ctx.writeAndFlush("欢迎加入\n");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("Client:"+incoming.remoteAddress() +"离开");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client > " + msg);
        // 返回客户端消息
        ctx.writeAndFlush("我已经接受到了你的消息:"+msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
        System.out.println("Unexpected exception from downstream." +  cause);
        //出现异常时关闭channel
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ctx.fireUserEventTriggered(evt);
    }

//    /**
//     * 接收到消息触发，消息处理
//     * @param ctx
//     * @param msg
//     * @throws Exception
//     */
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try {
//            ByteBuf in = (ByteBuf) msg;
//            System.out.println(in.toString(CharsetUtil.UTF_8));
//        } finally {
//            ReferenceCountUtil.release(msg);
//        }
//    }
//
//    // 异常时触发
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        cause.printStackTrace();
//        ctx.close();
//    }
}
