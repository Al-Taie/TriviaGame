package com.altaie.triviagame.data.repository

import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.network.Client
import com.altaie.triviagame.data.response.category.Category
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.data.response.quiz.Quiz
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Types
import io.reactivex.rxjava3.core.Observable


object TrivialRepository {
    val categoriesList = mutableListOf<Category>()
    private val quizList = mutableListOf<Quiz>()
    var progressState = 1
    var score = 0
    val currentQuestion get() = quizList[position]

    fun initQuizList(quizList: List<Quiz>?) {
        TrivialRepository.quizList.clear()
        quizList?.let {
            TrivialRepository.quizList.addAll(quizList)
        }

    }

    fun initCategoriesList(category: List<Category>) = categoriesList.addAll(category)

    var category: String = ""
    val categories get() = categoriesList
    val quizzes get() = quizList

    fun getQuizList(): Observable<Status<NationalQuizResponse>> {
        return Observable.create { emitter ->
            emitter.onNext(Status.Loading)
            emitter.onNext(Client.initRequest())
            emitter.onComplete()
        }
    }

    object Settings {
        var category: String = "9"
        var categoryName: String = "General Knowledge"
        var difficulty = Constant.EASY
        var type = Types.MULTIPLE
    }

    var position = 0
    val lastPosition get() = quizList.lastIndex
}
