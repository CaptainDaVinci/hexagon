package com.hexagonkt.http.client.netty

import com.hexagonkt.http.client.HttpClient
import com.hexagonkt.http.client.HttpClientPort
import com.hexagonkt.http.client.model.HttpClientRequest
import com.hexagonkt.http.client.model.HttpClientResponse

class NettyClientAdapter : HttpClientPort {
    override fun startUp(client: HttpClient) {
        TODO("Not yet implemented")
    }

    override fun shutDown() {
        TODO("Not yet implemented")
    }

    override fun send(request: HttpClientRequest): HttpClientResponse {
        TODO("Not yet implemented")
    }
}
