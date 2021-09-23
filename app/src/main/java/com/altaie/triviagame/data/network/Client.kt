package com.altaie.triviagame.data.network

import android.util.Log
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Params
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

object Client {
    private val okHttpClient = OkHttpClient()
    private val url = "https://opentdb.com/api_category.php"
    private val gson = Gson()


    fun getCategory() {
        val request = Request.Builder().url(url).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v("request_category", "fail: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { categorySting ->
                    val result = gson.fromJson(categorySting, NationalCategoryResponse::class.java)
                    Log.v("test", result.toString())

                }
            }
        })
    }


    fun initRequest() : Status<NationalQuizResponse> {
        val url = HttpUrl.Builder()
            .scheme(Constant.SCHEMA)
            .host(Constant.HOST)
            .addPathSegment(Constant.PATH)
            .addQueryParameter(Params.AMOUNT, Constant.AMOUNT)
            .addQueryParameter(Params.CATEGORY, TrivialRepository.Settings.category)
            .addQueryParameter(Params.DIFFICULTY, TrivialRepository.Settings.difficulty)
            .addQueryParameter(Params.TYPE, TrivialRepository.Settings.type)
            .build()

        val request = Request.Builder().url(url = url).build()
        return getQuestion(request = request)
    }

    private fun getQuestion(request: Request) : Status<NationalQuizResponse>{
        val response = okHttpClient.newCall(request = request).execute()

        return if (response.isSuccessful) {
            Gson().fromJson(response.body?.string(), NationalQuizResponse::class.java).run {
                Log.v("result",this.toString())
                Status.Success(this)
            }
        } else {
            Status.Fail(response.message)
        }
    }

}