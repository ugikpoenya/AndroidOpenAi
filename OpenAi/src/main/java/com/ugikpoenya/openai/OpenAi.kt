package com.ugikpoenya.openai

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.ugikpoenya.openai.api.ApiClient
import com.ugikpoenya.openai.api.ApiService
import com.ugikpoenya.openai.model.CompletionModel
import com.ugikpoenya.openai.model.ErrorResponse
import com.ugikpoenya.openai.model.ImageRequest
import com.ugikpoenya.openai.model.ImageResponse
import com.ugikpoenya.openai.model.ModelResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class OpenAi(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(context.packageName, 0)
    var OPENAI_API_KEY: String
        get() = prefs.getString("OPENAI_API_KEY", "").toString()
        set(value) = prefs.edit().putString("OPENAI_API_KEY", value).apply()

    fun getModels(function: (response: ModelResponse?, error: ErrorResponse?) -> (Unit)) {
        val apiService = ApiClient.client!!.create(ApiService::class.java)
        val call: Call<ModelResponse> = apiService.getModels("Bearer $OPENAI_API_KEY")
        call.enqueue(object : Callback<ModelResponse> {
            override fun onResponse(call: Call<ModelResponse>, response: Response<ModelResponse>) {
                if (response.isSuccessful) {
                    function(response.body(), null)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    Log.d("LOG", "errorBody " + errorResponse.error?.message)
                    function(null, errorResponse)
                }
            }

            override fun onFailure(call: Call<ModelResponse>, t: Throwable) {
                Log.d("LOG", "onFailure " + t.localizedMessage)
                function(null, null)
            }
        })
    }

    fun postChatCompletions(completion: CompletionModel, function: (response: CompletionModel?, error: ErrorResponse?) -> (Unit)) {
        val apiService = ApiClient.client!!.create(ApiService::class.java)
        val call: Call<CompletionModel> = apiService.postChatCompletions("Bearer $OPENAI_API_KEY", completion)
        call.enqueue(object : Callback<CompletionModel> {
            override fun onResponse(call: Call<CompletionModel>, response: Response<CompletionModel>) {
                if (response.isSuccessful) {
                    function(response.body(), null)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    Log.d("LOG", "errorBody " + errorResponse.error?.message)
                    function(null, errorResponse)
                }
            }

            override fun onFailure(call: Call<CompletionModel>, t: Throwable) {
                Log.d("LOG", "onFailure " + t.localizedMessage)
                function(null, null)
            }
        })
    }

    fun postImagesGenerations(imageRequest: ImageRequest?, function: (response: ImageResponse?, error: ErrorResponse?) -> (Unit)) {
        val apiService = ApiClient.client!!.create(ApiService::class.java)
        val call: Call<ImageResponse> = apiService.postImagesGenerations("Bearer $OPENAI_API_KEY", imageRequest)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    function(response.body(), null)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    Log.d("LOG", "errorBody " + errorResponse.error?.message)
                    function(null, errorResponse)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("LOG", "onFailure " + t.localizedMessage)
                function(null, null)
            }
        })
    }

    fun postImagesEdits(imageRequest: ImageRequest?, function: (response: ImageResponse?, error: ErrorResponse?) -> (Unit)) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("prompt", imageRequest?.prompt.toString())

        if (imageRequest?.size != null) builder.addFormDataPart("size", imageRequest.size.toString())
        if (imageRequest?.n != null) builder.addFormDataPart("n", imageRequest.n.toString())
        if (imageRequest?.response_format != null) builder.addFormDataPart("response_format", imageRequest.response_format.toString())


        if (imageRequest?.image != null) {
            val image = File(imageRequest.image.toString())
            builder.addFormDataPart("image", image.name, image.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
        }

        if (imageRequest?.mask != null) {
            val mask = File(imageRequest.mask.toString())
            builder.addFormDataPart("mask", mask.name, mask.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
        }

        val requestBody = builder.build()
        val apiService = ApiClient.client!!.create(ApiService::class.java)
        val call: Call<ImageResponse> = apiService.postImagesEdits("Bearer $OPENAI_API_KEY", requestBody)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    function(response.body(), null)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    Log.d("LOG", "errorBody " + errorResponse.error?.message)
                    function(null, errorResponse)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("LOG", "onFailure " + t.localizedMessage)
                function(null, null)
            }
        })
    }

    fun postImagesVariations (imageRequest: ImageRequest?, function: (response: ImageResponse?, error: ErrorResponse?) -> (Unit)) {
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        if (imageRequest?.size != null) builder.addFormDataPart("size", imageRequest.size.toString())
        if (imageRequest?.n != null) builder.addFormDataPart("n", imageRequest.n.toString())
        if (imageRequest?.response_format != null) builder.addFormDataPart("response_format", imageRequest.response_format.toString())


        if (imageRequest?.image != null) {
            val image = File(imageRequest.image.toString())
            builder.addFormDataPart("image", image.name, image.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
        }

        val requestBody = builder.build()
        val apiService = ApiClient.client!!.create(ApiService::class.java)
        val call: Call<ImageResponse> = apiService.postImagesVariations("Bearer $OPENAI_API_KEY", requestBody)
        call.enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    function(response.body(), null)
                } else {
                    val errorResponse = Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    Log.d("LOG", "errorBody " + errorResponse.error?.message)
                    function(null, errorResponse)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                Log.d("LOG", "onFailure " + t.localizedMessage)
                function(null, null)
            }
        })
    }
}