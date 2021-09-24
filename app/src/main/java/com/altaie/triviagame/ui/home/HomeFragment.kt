package com.altaie.triviagame.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.repository.TrivialRepository.categories
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
import com.altaie.triviagame.databinding.FragmentHomeBinding
import com.altaie.triviagame.ui.MainActivity
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Image
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {
        if (categories.isEmpty()) {
            getCategory()
        } else
            binding.categoryRecycler.adapter = CategoryAdapter(categories)
    }

    override fun callBack() {
        initDifficultyRadioButton()
        binding.apply {
            playButton.setOnClickListener {
                (activity as MainActivity).getQuizList()
            }
        }
    }

    private fun initDifficultyRadioButton() {
        binding.apply {
            chipEasy.setOnClickListener {
                TrivialRepository.Settings.difficulty = Constant.EASY
            }
            chipMedium.setOnClickListener {
                TrivialRepository.Settings.difficulty = Constant.MEDIUM
            }
            chipHard.setOnClickListener {
                TrivialRepository.Settings.difficulty = Constant.HARD
            }
        }
    }

    private fun getCategory() {
        val url = "https://opentdb.com/api_category.php"
        val request = Request.Builder().url(url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v("request_category", "fail: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string().let { categorySting ->
                    var index = 0
                    val result =
                        Gson().fromJson(categorySting, NationalCategoryResponse::class.java)
                    val categories = result.categories.map { category ->
                        category.apply {
                            name = name.removePrefix("Entertainment: ")
                                .removePrefix("Science: ")

                            imageId = Image.image[index]
                            index++
                        }
                    }.toMutableList()

                    val temp = categories[0]
                    categories[0] = categories[1]
                    categories[10] = temp

                    TrivialRepository.initCategoriesList(categories)

                    activity?.runOnUiThread {
                        binding.categoryRecycler.adapter = CategoryAdapter(categories)
                    }

                }
            }
        })
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

}