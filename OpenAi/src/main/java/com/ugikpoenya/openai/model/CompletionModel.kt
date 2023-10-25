package com.ugikpoenya.openai.model

class CompletionModel(model: String) {
    var id: String? = null
    var created: Int? = null
    var max_tokens: Int? = null
    var model: String? = model
    var stream: Boolean = true
    var choices: ArrayList<ChoiceModel>? = null
    var messages: ArrayList<MessageModel>? = null
    var usage: UsageModel? = null
}