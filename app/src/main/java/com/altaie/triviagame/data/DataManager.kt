package com.altaie.triviagame.data

import com.altaie.triviagame.R

object DataManager {
    private val categoriesList = mutableListOf<Category>()
    private val quizList = mutableListOf<Quiz>()
    private val categoriesMap = mutableMapOf<String, String>()

    fun initCategories(categories: Array<String>) {
        categoriesList.clear()

        categories.forEach {
            val item = it.split(':')
            categoriesMap[item[1]] = item[0]
            categoriesList.add(Category(name = item[1], imageId = R.drawable.ic_sports))
        }
    }

    fun initQuizList(quizList: List<Quiz>) {
        this.quizList.clear()
        this.quizList.addAll(quizList)
    }

    var category: String = ""
    val categories get() = categoriesList
    val categoryMap get() = categoriesMap
    val quizzes get() = quizList
}