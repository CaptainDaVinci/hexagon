package com.hexagonkt.http.client.netty

import com.hexagonkt.core.Jvm
import com.hexagonkt.http.client.HttpClient
import com.hexagonkt.http.client.HttpClientPort
import com.hexagonkt.http.model.HttpRequestPort
import com.hexagonkt.http.model.HttpResponsePort
import com.hexagonkt.http.model.ServerEvent
import com.hexagonkt.http.model.ws.WsSession
import io.netty.bootstrap.Bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.MultithreadEventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import java.lang.UnsupportedOperationException
import java.util.concurrent.Flow.Publisher

/**
 Client to use other REST services
 */
open class NettyClientAdapter(
    private val workerGroupThreads: Int
) : HttpClientPort {
    private var started: Boolean = false
    private var workerEventLoop: MultithreadEventLoopGroup? = null

    constructor() : this(
        workerGroupThreads = 1,
    )

    override fun startUp(client: HttpClient) {
        val workerGroup = groupSupplier(workerGroupThreads)
        val bootstrap = bootstrapSupplier(workerGroup)

        workerEventLoop = workerGroup

        // TODO: Implement client

        started = true
    }

    override fun shutDown() {
        workerEventLoop?.shutdownGracefully()
        started = false
    }

    override fun started(): Boolean = started

    override fun send(request: HttpRequestPort): HttpResponsePort {
        TODO("Not yet implemented")
    }

    override fun sse(request: HttpRequestPort): Publisher<ServerEvent> {
        TODO("Not yet implemented")
    }

    override fun ws(
        path: String,
        onConnect: WsSession.() -> Unit,
        onBinary: WsSession.(data: ByteArray) -> Unit,
        onText: WsSession.(text: String) -> Unit,
        onPing: WsSession.(data: ByteArray) -> Unit,
        onPong: WsSession.(data: ByteArray) -> Unit,
        onClose: WsSession.(status: Int, reason: String) -> Unit,
    ): WsSession {
        throw UnsupportedOperationException("WebSockets not supported.")
    }

    open fun groupSupplier(it: Int): MultithreadEventLoopGroup =
        NioEventLoopGroup(it)

    open fun bootstrapSupplier(
        workerGroup: MultithreadEventLoopGroup,
    ): Bootstrap =
        Bootstrap().group(workerGroup)
            .channel(NioSocketChannel::class.java)
            .option(ChannelOption.SO_KEEPALIVE, true)
}
