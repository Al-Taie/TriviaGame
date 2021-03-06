package com.altaie.triviagame.ui

import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.Status
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.data.response.quiz.NationalQuizResponse
import com.altaie.triviagame.databinding.ActivityMainBinding
import com.altaie.triviagame.ui.base.BaseActivity
import com.altaie.triviagame.ui.challenge.ChallengeFragment
import com.altaie.triviagame.ui.interfaces.ItemListener
import com.altaie.triviagame.util.slideVisibility
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : BaseActivity<ActivityMainBinding>(), ItemListener {
    override val theme: Int
        get() = R.style.Theme_TriviaGame

    override fun setup() {
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

    fun getQuizList() {
        disposable.add(
            TrivialRepository.getQuizList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onQuizResponse, ::onError)
        )
    }

    private fun onError(e: Throwable) {
        binding.loading.apply {
            setAnimation(R.raw.no_connection)
            playAnimation()

            Handler(Looper.getMainLooper()).postDelayed({
                slideVisibility(visibility = false)
                binding.fragmentContainer.slideVisibility(visibility = true)
            }, 4200L)
        }
    }

    private fun onQuizResponse(response: Status<NationalQuizResponse>) {
        when (response) {
            is Status.Fail -> {
                binding.loading.slideVisibility(visibility = false)
                binding.fragmentContainer.slideVisibility(visibility = true)
            }
            is Status.Loading -> {
                binding.loading.apply {
                    setAnimation(R.raw.loading)
                    playAnimation()
                    slideVisibility(visibility = true)
                }
                binding.fragmentContainer.slideVisibility(visibility = false, gravity = Gravity.TOP)
            }
            is Status.Success -> {
                TrivialRepository.initQuizList(response.data.results)
                val fragment = ChallengeFragment()
                replaceFragment(fragment = fragment)
                binding.loading.slideVisibility(visibility = false)
                binding.fragmentContainer.slideVisibility(visibility = true)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }.commit()

    }
}