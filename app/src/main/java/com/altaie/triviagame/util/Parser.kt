package com.altaie.triviagame.util

import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.data.response.quiz.NationalResponse

import com.google.gson.Gson

object Parser {
    fun parse(response: String) {
        val nationalResponse = Gson().fromJson(response, NationalResponse::class.java)
        DataManager.initQuizList(nationalResponse.results)
    }
}