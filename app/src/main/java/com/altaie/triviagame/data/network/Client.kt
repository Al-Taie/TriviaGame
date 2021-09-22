package com.altaie.triviagame.data.network

import android.util.Log
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
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

    fun getQuestion(request: Request) {
        okHttpClient.newCall(request = request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v("FAILURE_REQUEST", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { Parser.parse(it.string()) }

            }
        })
    }

}