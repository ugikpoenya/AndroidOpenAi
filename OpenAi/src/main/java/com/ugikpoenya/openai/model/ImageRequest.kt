package com.ugikpoenya.openai.model

class ImageRequest {
    var prompt: String? = null
    var n: String? = null
    var size: String? = null
    var response_format: String? = null
    var image: String? = null
    var mask: String? = null

    constructor(prompt: String?) {
        this.prompt = prompt
    }

    constructor(prompt: String?, image: String?) {
        this.prompt = prompt
        this.image = image
    }


    constructor(prompt: String?, image: String?, mask: String) {
        this.prompt = prompt
        this.image = image
        this.mask = mask
    }

}