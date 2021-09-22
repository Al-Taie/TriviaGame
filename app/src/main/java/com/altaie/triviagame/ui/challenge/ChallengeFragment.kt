package com.altaie.triviagame.ui.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R

import com.altaie.triviagame.databinding.FragmentChallengeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.UpdateAdapter
import com.altaie.triviagame.ui.result.ResultFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(), UpdateAdapter {
    override fun setup() {}

    override fun callBack() {
        binding.optionFour.setOnClickListener {
            val fragment = ResultFragment()
            replaceFragment(fragment = fragment)
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentChallengeBinding
        get() = FragmentChallengeBinding::inflate

    override fun update() {}

    private fun replaceFragment(fragment: Fragment) {
        activity?.let {
            it.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }.commit()
        }
    }
}