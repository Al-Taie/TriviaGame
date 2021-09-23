package com.altaie.triviagame.data.network

import android.util.Log
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Params
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()

    fun initRequest(): Status<NationalQuizResponse> {
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

    private fun getQuestion(request: Request): Status<NationalQuizResponse> {
        val response = okHttpClient.newCall(request = request).execute()

        return if (response.isSuccessful) {
            Gson().fromJson(response.body?.string(), NationalQuizResponse::class.java).run {
                Log.v("result", this.toString())
                Status.Success(this)
            }
        } else {
            Status.Fail(response.message)
        }
    }

}