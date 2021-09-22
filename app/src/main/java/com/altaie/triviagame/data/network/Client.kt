package com.altaie.triviagame.data.network

import android.util.Log
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Params
import com.altaie.triviagame.util.Parser
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


    private fun initRequest(category: String, difficulty: String, type: String) : Status<NationalQuizResponse> {
        val url = HttpUrl.Builder()
            .scheme(Constant.SCHEMA)
            .host(Constant.HOST)
            .addPathSegment(Constant.PATH)
            .addQueryParameter(Params.AMOUNT, Constant.AMOUNT)
            .addQueryParameter(Params.CATEGORY, category)
            .addQueryParameter(Params.DIFFICULTY, difficulty)
            .addQueryParameter(Params.TYPE, type)
            .build()

        val request = Request.Builder().url(url = url).build()
        return getQuestion(request = request)
    }

    private fun getQuestion(request: Request) : Status<NationalQuizResponse>{
        val response = okHttpClient.newCall(request = request).execute()
        val nationalResponse = Gson().fromJson(response.body.toString(), NationalQuizResponse::class.java)

        return if (response.isSuccessful) {
           Status.Success(nationalResponse)
        } else {
            Status.Fail(response.message)
        }
    }

}