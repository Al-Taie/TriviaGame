package com.altaie.triviagame.ui.challenge

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.data.repository.TrivialRepository
import com.altaie.triviagame.databinding.FragmentChallengeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.UpdateAdapter
import com.altaie.triviagame.ui.result.ResultFragment


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(), UpdateAdapter {
    override fun setup() {}

    override fun callBack() {
        binding.optionFour.setOnClickListener {
            val fragment = ResultFragment()
            replaceFragment(fragment = fragment)
        }

        bindData()
//        setOptions()

    }

    private fun setOptions() {
        binding.optionsGroup.children.forEach { button ->
            button.setOnClickListener {
                val correctAnswer = TrivialRepository.currentQuestion.correctAnswer
                val currentAnswer = (button as RadioButton).text
                if (correctAnswer == currentAnswer){
                    TrivialRepository.score += 10
                }
                TrivialRepository.position++
                bindData()
            }
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