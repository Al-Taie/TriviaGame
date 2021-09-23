package com.altaie.triviagame.ui.home

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.repository.TrivialRepository.categories
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.databinding.FragmentHomeBinding
import com.altaie.triviagame.ui.MainActivity
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.challenge.ChallengeFragment
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Image
import com.altaie.triviagame.util.slideVisibility
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {
        if (categories.isEmpty()) {
            getCategory()
        }else
            activity?.runOnUiThread {
            binding.categoryRecycler.adapter = CategoryAdapter(categories)
        }


    }

    override fun callBack() {
        initDifficultyRadioButton()
        binding.apply {
            next.setOnClickListener {
                val fragment = ChallengeFragment()
                replaceFragment(fragment = fragment)
            }
        }
    }

    private fun initDifficultyRadioButton() {
        binding.apply {
            Easy.setOnClickListener {
                TrivialRepository.Settings.difficulty = Constant.EASY
            }
            middle.setOnClickListener {
                TrivialRepository.Settings.difficulty = Constant.MEDIUM
            }
            Difficult.setOnClickListener {
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
                    var index=0
                    val result = Gson().fromJson(categorySting, NationalCategoryResponse::class.java)
                    val categories = result.categories.map { category ->
                        category.apply {
                            name = name.removePrefix("Entertainment: ")
                                       .removePrefix("Science: ")

                             imageId=Image.image[index]
                            index++
                        }

                    }
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

    private fun replaceFragment(fragment: Fragment) {
        activity?.let {
            it.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }.commit()
        }
    }
}