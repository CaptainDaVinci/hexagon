package com.hexagonkt.http.model

import com.hexagonkt.http.model.HttpStatusType.*

/**
 * Supported HTTP responses status.
 *
 * TODO Rename by ClientError for discovery reasons (apply to all enums)
 */
enum class ClientErrorStatus(
    override val code: Int,
    override val type: HttpStatusType
) : HttpStatus {

    // TODO Rename with BAD_REQUEST_400 for ease of use (apply to all statuses)
    BAD_REQUEST(400, CLIENT_ERROR),
    UNAUTHORIZED(401, CLIENT_ERROR),
    PAYMENT_REQUIRED(402, CLIENT_ERROR),
    FORBIDDEN(403, CLIENT_ERROR),
    NOT_FOUND(404, CLIENT_ERROR),
    METHOD_NOT_ALLOWED(405, CLIENT_ERROR),
    NOT_ACCEPTABLE(406, CLIENT_ERROR),
    PROXY_AUTHENTICATION_REQUIRED(407, CLIENT_ERROR),
    REQUEST_TIMEOUT(408, CLIENT_ERROR),
    CONFLICT(409, CLIENT_ERROR),
    GONE(410, CLIENT_ERROR),
    LENGTH_REQUIRED(411, CLIENT_ERROR),
    PRECONDITION_FAILED(412, CLIENT_ERROR),
    CONTENT_TOO_LARGE(413, CLIENT_ERROR),
    URI_TOO_LONG(414, CLIENT_ERROR),
    UNSUPPORTED_MEDIA_TYPE(415, CLIENT_ERROR),
    RANGE_NOT_SATISFIABLE(416, CLIENT_ERROR),
    EXPECTATION_FAILED(417, CLIENT_ERROR),
    I_AM_A_TEAPOT(418, CLIENT_ERROR),
    MISDIRECTED_REQUEST(421, CLIENT_ERROR),
    UNPROCESSABLE_CONTENT(422, CLIENT_ERROR),
    LOCKED(423, CLIENT_ERROR),
    FAILED_DEPENDENCY(424, CLIENT_ERROR),
    TOO_EARLY(425, CLIENT_ERROR),
    UPGRADE_REQUIRED(426, CLIENT_ERROR),
    PRECONDITION_REQUIRED(428, CLIENT_ERROR),
    TOO_MANY_REQUESTS(429, CLIENT_ERROR),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, CLIENT_ERROR),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, CLIENT_ERROR),
}
