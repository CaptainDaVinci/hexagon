package com.hexagonkt.http.client.netty

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class NettyClientInitializer : ChannelInitializer<SocketChannel>() {
    val responseHandler = NettyClientResponseHandler()

    override fun initChannel(ch: SocketChannel) {
        ch.pipeline().addLast(responseHandler)
    }
}
