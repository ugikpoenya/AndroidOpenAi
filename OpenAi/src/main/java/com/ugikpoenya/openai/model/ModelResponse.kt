package com.ugikpoenya.openai.model


class ModelResponse {
    var data: ArrayList<Model>? = null
}

class Model {
    var id: String? = null
    var created: String? = null
    var owned_by: String? = null
    var root: String? = null
}