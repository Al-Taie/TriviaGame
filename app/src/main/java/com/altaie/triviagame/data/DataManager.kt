package com.altaie.triviagame.data

object DataManager {
    private val categoriesList = mutableListOf<Category>()
    private val quizList = mutableListOf<Quiz>()
    private val categoriesMap = mutableMapOf<String, String>()

    fun initQuizList(quizList: List<Quiz>) {
        this.quizList.clear()
        this.quizList.addAll(quizList)
    }

    var category: String = ""
    val categories get() = categoriesList
    val categoryMap get() = categoriesMap
    val quizzes get() = quizList
}