package com.ugikpoenya.openai.api

import com.ugikpoenya.openai.model.CompletionModel

interface CompletionListener {
    fun updateContent(content: String?)
    fun updateResponse(response: String?) {}
    fun updateModel(completionModel: CompletionModel?) {}
}