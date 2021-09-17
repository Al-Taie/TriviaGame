package com.altaie.triviagame.data

import com.google.gson.annotations.SerializedName

data class NationalResponse(
    @SerializedName("response_code")
    val responseCode: Int,

    @SerializedName("results")
    val results: List<Quiz>
)
