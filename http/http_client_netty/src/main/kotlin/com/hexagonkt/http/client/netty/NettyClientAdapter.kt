package com.hexagonkt.http.client.netty

import com.hexagonkt.http.bodyToBytes
import com.hexagonkt.http.client.HttpClient
import com.hexagonkt.http.client.HttpClientPort
import com.hexagonkt.http.model.*
import com.hexagonkt.http.model.ws.WsSession
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled.wrappedBuffer
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.DefaultFullHttpRequest
import io.netty.handler.codec.http.FullHttpResponse
import io.netty.handler.codec.http.HttpMethod
import io.netty.handler.codec.http.HttpVersion
import java.net.URI
import java.util.concurrent.Flow.Publisher

/**
 Client to use other REST services
 */
open class NettyClientAdapter(
    private val workerGroupThreads: Int,
) : HttpClientPort {
    private var started: Boolean = false
    private var workerEventLoop: MultithreadEventLoopGroup? = null
    protected lateinit var httpClient: HttpClient
    private var bootstrap: Bootstrap? = null
    private val initializer = NettyClientInitializer()

    constructor() : this(
        workerGroupThreads = 1,
    )

    override fun startUp(client: HttpClient) {
        val workerGroup = groupSupplier(workerGroupThreads)
        httpClient = client
        bootstrap = bootstrapSupplier(workerGroup)
        workerEventLoop = workerGroup
        started = true
    }

    override fun shutDown() {
        workerEventLoop?.shutdownGracefully()
        started = false
    }

    override fun started(): Boolean = started

    override fun send(request: HttpRequestPort): HttpResponsePort {
        val baseUrl = httpClient.settings.baseUrl
        val uri = (baseUrl ?: request.url()).toURI()
        val channel = bootstrap?.connect(uri.host, uri.port)?.sync()?.channel()
        val httpRequest = DefaultFullHttpRequest(
            HttpVersion.HTTP_1_1,
            HttpMethod.valueOf(request.method.toString()),
            URI((baseUrl?.toString() ?: "") + request.path).toString(),
            wrappedBuffer(bodyToBytes(request.body)),
        )

        val settings = httpClient.settings
        val contentType = request.contentType ?: settings.contentType
        val authorization = request.authorization ?: settings.authorization
        httpRequest.headers().apply {
            this.remove("accept-encoding") // Don't send encoding by default
            if (contentType != null) {
                this.add("content-type", contentType.text)
            }
            if (authorization != null) {
                this.add("authorization", authorization.text)
            }
            (settings.headers + request.headers).values
                .forEach { (k, v) -> this.add(k, v.map(Any::toString)) }
        }

        val responseHandler = initializer.responseHandler
        channel?.writeAndFlush(request)
        channel?.closeFuture()?.sync()

        return convertResponseToHttpResponsePort(responseHandler.response)
    }

    private fun convertResponseToHttpResponsePort(response: FullHttpResponse): HttpResponsePort {
        val body = response.content()
        val status = response.status().code()
        return HttpResponse(body = body, status = HttpStatus(status))
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
            .handler(initializer)
}
