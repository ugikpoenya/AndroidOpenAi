package com.ugikpoenya.openai.model

class ChoiceModel {
    var index: Int? = null
    var message: MessageModel? = null
    var delta: DeltaModel? = null
    var finish_reason: String? = null
}

class DeltaModel {
    var content: String? = null
}