package com.hexagonkt.http.server.callbacks

import com.hexagonkt.core.ResourceNotFoundException
import com.hexagonkt.core.media.mediaTypeOf
import com.hexagonkt.http.model.ContentType
import com.hexagonkt.http.server.handlers.HttpServerContext
import java.net.URL

class UrlCallback(private val url: URL) : (HttpServerContext) -> HttpServerContext {

    override fun invoke(context: HttpServerContext): HttpServerContext {
        // TODO Allow targeting a single file if no path parameter is found
        // TODO Trim starting backslashes
        val requestPath = context.pathParameters["0"]
            ?: error("URL loading require a single path parameter")

        return try {
            if (requestPath.endsWith("/"))
                throw ResourceNotFoundException("$requestPath not found (folder)")

            val resource = URL("$url/$requestPath")
            val bytes = resource.readBytes()
            val mediaType = mediaTypeOf(resource)
            context.ok(bytes, contentType = ContentType(mediaType))
        }
        catch (e: ResourceNotFoundException) {
            context.notFound(e.message ?: "")
        }
    }
}
