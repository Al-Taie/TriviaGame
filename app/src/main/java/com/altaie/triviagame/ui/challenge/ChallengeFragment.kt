package com.altaie.triviagame.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.databinding.FragmentChallengeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.UpdateAdapter
import com.altaie.triviagame.ui.result.ResultFragment
import com.altaie.triviagame.util.Constant


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(), UpdateAdapter {
    override fun setup() {}

    override fun callBack() {
        bindData()
        setOptions()
    }

    private fun setOptions() {
        binding.optionsGroup.children.forEach { button ->
            button.setOnClickListener {
                val correctAnswer = TrivialRepository.currentQuestion.correctAnswer
                val currentAnswer = (button as AppCompatButton).text
                if (correctAnswer == currentAnswer) {
                    TrivialRepository.score += 10
                }
                checkEnd()
            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentChallengeBinding
        get() = FragmentChallengeBinding::inflate

    override fun update() {}

    private fun checkEnd() {
        val result = TrivialRepository.score.toString()
        val position = TrivialRepository.position
        val fragment = ResultFragment()
        val bundle = Bundle()

        bundle.putString(Constant.RESULT_KEY, result)
        fragment.arguments = bundle

        if (position < TrivialRepository.quizzes.lastIndex) {
            TrivialRepository.position++
            bindData()
        } else {
            TrivialRepository.position = 0
            TrivialRepository.score = 0
            replaceFragment(fragment = fragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.let {
            it.supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragment_container, fragment)
                addToBackStack(null)
            }.commit()
        }
    }

    private fun bindData() {
        TrivialRepository.quizzes[TrivialRepository.position].apply {
            val list = mutableListOf<String>()
            list.addAll(incorrectAnswers)
            list.add(correctAnswer)
            list.shuffle()
            binding.question.text = question

            binding.apply {
                optionOne.text = list[0]
                optionTwo.text = list[1]
                optionThree.text = list[2]
                optionFour.text = list[3]
            }
        }
    }
}