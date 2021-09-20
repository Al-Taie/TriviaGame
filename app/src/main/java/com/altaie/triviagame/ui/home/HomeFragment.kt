package com.altaie.triviagame.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.data.category.Category
import com.altaie.triviagame.data.category.NationalCategoryResponse
import com.altaie.triviagame.databinding.FragmentHomeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.ItemListener
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class HomeFragment(private val listener: ItemListener) : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {
        getCategory()
    }

    override fun callBack() {}

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
                    Log.v("category",result.categories.toString())
                    activity?.runOnUiThread {
                        binding.categoryRecycler.adapter = CategoryAdapter(result.categories, listener)
                    }

                }
            }

        })
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
}