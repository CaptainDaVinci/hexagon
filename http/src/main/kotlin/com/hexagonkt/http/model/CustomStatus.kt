package com.hexagonkt.http.model

import com.hexagonkt.core.disableChecks
import com.hexagonkt.http.model.HttpStatusType.*
import java.lang.IllegalArgumentException

data class CustomStatus(
    override val code: Int,
    override val type: HttpStatusType = when (code) {
        in 100..199 -> INFORMATION
        in 200..299 -> SUCCESS
        in 300..399 -> REDIRECTION
        in 400..499 -> CLIENT_ERROR
        in 500..599 -> SERVER_ERROR
        else -> throw IllegalArgumentException(INVALID_CODE_ERROR_MESSAGE + code)
    }
) : HttpStatus {

    internal companion object {
        const val INVALID_CODE_ERROR_MESSAGE: String =
            "Error code is not in any HTTP status range (100..599): "
    }

    init {
        if (!disableChecks)
            require(code in 100..599) { INVALID_CODE_ERROR_MESSAGE + code }
    }
}
