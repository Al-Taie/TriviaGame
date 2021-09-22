package com.altaie.triviagame.data.response.quiz

import com.google.gson.annotations.SerializedName

data class NationalQuizResponse(
    @SerializedName("response_code")
    val responseCode: Int,

    @SerializedName("results")
    val results: List<Quiz>
)
