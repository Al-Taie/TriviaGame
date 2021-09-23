package com.altaie.triviagame.ui.result

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.databinding.FragmentResultBinding
import com.altaie.triviagame.ui.MainActivity
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.challenge.ChallengeFragment
import com.altaie.triviagame.ui.home.HomeFragment
import com.altaie.triviagame.util.Constant
import java.util.*


class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val result = it.getString(Constant.RESULT_KEY)
            "$result%".also { it1 -> binding.result.text = it1 }
            result?.let { it1 -> resultStatus(it1.toInt()) }
        }
    }

    private fun resultStatus(score: Int) {
        binding.apply {
            when {
                score >= 90 -> {
                    setResultStatus(
                        textId = R.string.Excellent,
                        colorId = R.color.dark_green,
                        animationId = R.raw.cup
                    )
                }
                score >= 80 -> {
                    setResultStatus(
                        textId = R.string.veryGood,
                        colorId = R.color.purple_700,
                        animationId = R.raw.trophy
                    )
                }
                score >= 70 -> {
                    setResultStatus(
                        textId = R.string.Good,
                        colorId = R.color.purple_500,
                        animationId = R.raw.thumps_up
                    )
                }
                score >= 60 -> {
                    setResultStatus(
                        textId = R.string.Medium,
                        colorId = R.color.blue,
                        animationId = R.raw.thumps_up
                    )
                }
                score >= 50 -> {
                    setResultStatus(
                        textId = R.string.Passable,
                        colorId = R.color.darkGrey,
                        animationId = R.raw.thumps_up
                    )
                }
                else -> {
                    setResultStatus(
                        textId = R.string.Weak,
                        colorId = R.color.dark_red,
                        animationId = R.raw.cup
                    )
                }
            }
        }
    }


    fun setResultStatus(textId: Int, colorId: Int, animationId: Int) {
        binding.apply {
            val color = getColor(requireContext(), colorId)
            resultStatus.text = getString(textId)
            resultStatus.setTextColor(color)
            result.setTextColor(color)
            lottieImg.setAnimation(animationId)
        }
    }


    override fun setup() {}

    override fun callBack() {
        binding.apply {
            startNewGameButton.setOnClickListener {
                replaceFragment(fragment = HomeFragment())
            }

            playAgainBtn.setOnClickListener {
                (activity as MainActivity).getQuizList()
                replaceFragment(fragment = ChallengeFragment())
            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate


    private fun replaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }?.commit()
    }
}