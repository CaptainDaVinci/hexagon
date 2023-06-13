package com.hexagonkt.http.client.netty

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.FullHttpResponse

class NettyClientResponseHandler : SimpleChannelInboundHandler<FullHttpResponse>() {
    lateinit var response: FullHttpResponse

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: FullHttpResponse?) {
        response = msg!!
    }
}
