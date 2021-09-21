package com.altaie.triviagame.ui.challenge

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.databinding.FragmentChallengeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.UpdateAdapter
import com.altaie.triviagame.ui.result.ResultFragment
import com.altaie.triviagame.util.Constant
import com.altaie.triviagame.util.Params
import com.altaie.triviagame.util.Parser
import okhttp3.*
import java.io.IOException


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(), UpdateAdapter {

    override fun setup() {
    }

    override fun callBack() {
        binding.optionFour.setOnClickListener {
            val fragment = ResultFragment()
            replaceFragment(fragment = fragment)
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentChallengeBinding
        get() = FragmentChallengeBinding::inflate

    private fun initRequest() {
        val url = HttpUrl.Builder()
            .scheme(Constant.SCHEMA)
            .host(Constant.HOST)
            .addPathSegment(Constant.PATH)
            .addQueryParameter(Params.AMOUNT, Constant.AMOUNT)
            .addQueryParameter(Params.CATEGORY, DataManager.categoryMap[DataManager.category])
            .addQueryParameter(Params.DIFFICULTY, Constant.EASY)
            .addQueryParameter(Params.TYPE, Constant.TYPE)
            .build()

        val request = Request.Builder().url(url = url).build()
        makeRequest(request = request)
    }

    private fun makeRequest(request: Request) {
        OkHttpClient().newCall(request = request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v("FAILURE_REQUEST", e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { Parser.parse(it.string()) }
                activity?.runOnUiThread {
//                    binding.challengeRecycler.adapter = ChallengeAdapter(DataManager.quizzes)
                }
            }
        })
    }

    override fun update() {
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.let {
            it.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }.commit()
        }
    }
}