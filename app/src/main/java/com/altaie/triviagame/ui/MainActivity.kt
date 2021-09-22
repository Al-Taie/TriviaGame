package com.altaie.triviagame.ui

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.altaie.triviagame.R
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.databinding.ActivityMainBinding
import com.altaie.triviagame.ui.base.BaseActivity
import com.altaie.triviagame.ui.interfaces.ItemListener
import com.altaie.triviagame.util.slideVisibility
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception


class MainActivity : BaseActivity<ActivityMainBinding>(), ItemListener {
    override val theme: Int
        get() = R.style.Theme_TriviaGame

    override fun setup() {
        getQuizList()
    }

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onClickItem(name: String) {
        TrivialRepository.category = name
    }

    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun getQuizList() {
        disposable.add(
            TrivialRepository.getQuizList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onQuizResponse, ::onError)
        )
    }

    private fun onError(e: Throwable) {
        Log.v("MAIN_ACTIVITY", e.message.toString())
    }

    private fun onQuizResponse(response: Status<NationalQuizResponse>) {
        when(response){
            is Status.Fail -> {
                binding.fragmentContainer.slideVisibility(visibility = true)
            }
            is Status.Loading -> {
//                binding.progressLoading.show()
                binding.fragmentContainer.slideVisibility(visibility = false, gravity = Gravity.TOP)
            }
            is Status.Success -> {
                binding.fragmentContainer.slideVisibility(visibility = true)
                TrivialRepository.initQuizList(response.data.results)
            }
        }
    }

}