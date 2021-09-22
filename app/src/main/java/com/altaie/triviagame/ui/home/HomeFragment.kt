package com.altaie.triviagame.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.data.DataManager.categories
import com.altaie.triviagame.data.response.category.NationalCategoryResponse
import com.altaie.triviagame.databinding.FragmentHomeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.challenge.ChallengeFragment
import com.google.gson.Gson
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
        binding.apply {
            next.setOnClickListener {
                val fragment = ChallengeFragment()
                replaceFragment(fragment = fragment)
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
                    val result = Gson().fromJson(categorySting, NationalCategoryResponse::class.java)
                    val categories = result.categories.map { category ->
                        category.apply {
                            name = name.removePrefix("Entertainment: ")
                                       .removePrefix("Science: ")
                        }

                    }
                    DataManager.initCategory(categories)


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