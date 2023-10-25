package com.ugikpoenya.openai.api

import com.ugikpoenya.openai.model.CompletionModel

interface CompletionListener {
    fun update(completionModel: CompletionModel)
}