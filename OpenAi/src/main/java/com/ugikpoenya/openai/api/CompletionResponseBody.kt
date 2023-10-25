package com.ugikpoenya.openai.api

import android.util.Log
import com.google.gson.Gson
import com.ugikpoenya.openai.model.CompletionModel
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.Source
import okio.buffer
import java.io.IOException


class CompletionResponseBody internal constructor(private val responseBody: ResponseBody, private val completionListener: CompletionListener) : ResponseBody() {
    private var bufferedSource: BufferedSource? = null
    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                try {
                    sink.readUtf8().lines().forEach {
                        if (it.isNotEmpty()) {
                            try {
                                val jsonString = it.substringAfter(":").trim()
                                val completion = Gson().fromJson(jsonString, CompletionModel::class.java)
                                completionListener.update(completion)
                            } catch (e: Exception) {
                                Log.d("LOG", it)
                                Log.d("LOG", e.message.toString())
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d("LOG", e.message.toString())
                }
                return super.read(sink, byteCount)
            }
        }
    }
}