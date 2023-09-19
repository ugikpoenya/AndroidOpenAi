package com.ugikpoenya.openai.model


class ErrorResponse {
    var error: Error? = null
}

class Error {
    var message: String? = null
    var type: String? = null
    var param: String? = null
    var code: String? = null
}