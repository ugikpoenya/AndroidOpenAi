package com.ugikpoenya.openai.api

import com.ugikpoenya.openai.model.CompletionModel
import com.ugikpoenya.openai.model.ImageRequest
import com.ugikpoenya.openai.model.ImageResponse
import com.ugikpoenya.openai.model.ModelResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


interface ApiService {
    @GET("models")
    fun getModels(@Header("Authorization") Authorization: String?): Call<ModelResponse>

    @POST("chat/completions")
    fun postChatCompletions(@Header("Authorization") Authorization: String?, @Body request: CompletionModel?): Call<CompletionModel>

    @POST("images/generations")
    fun postImagesGenerations(@Header("Authorization") Authorization: String?, @Body request: ImageRequest?): Call<ImageResponse>

    @POST("images/edits")
    fun postImagesEdits(@Header("Authorization") Authorization: String?, @Body file: RequestBody?): Call<ImageResponse>


}
