package com.altaie.triviagame.ui.challenge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.kofigyan.stateprogressbar.StateProgressBar
import java.util.*


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>(), UpdateAdapter {
    override fun setup() {

    }

    override fun callBack() {
        bindData()
        setOptions()
        binding.progress.apply {
            checkStateCompleted(true)
            setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
        }
        setTimer()
        fragmentChangeByTimer()
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
                setTimer()
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

        if (position < TrivialRepository.lastPosition) {
            TrivialRepository.position++
            bindData()
        } else {
            TrivialRepository.position = 0
            TrivialRepository.score = 0
            replaceFragment(fragment = fragment)
            TrivialRepository.progressState = 1
            binding.progress.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
        }

        updateProgressBar()
    }

    private fun updateProgressBar() {
        val position = TrivialRepository.position
        if (position % 3 == 0) {
            TrivialRepository.progressState++
            val progressState = when (TrivialRepository.progressState) {
                1 -> StateProgressBar.StateNumber.ONE
                2 -> StateProgressBar.StateNumber.TWO
                3 -> StateProgressBar.StateNumber.THREE
                else -> StateProgressBar.StateNumber.FOUR
            }
            binding.progress.setCurrentStateNumber(progressState)
        }

        binding.progress.apply {
            if (position == TrivialRepository.lastPosition) {
                setAllStatesCompleted(true)
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }?.commit()
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

    private fun setTimer() {
        val end = Calendar.getInstance()
        end.add(Calendar.SECOND, 10)
        val start = Calendar.getInstance()
        start.add(Calendar.SECOND, 0)
        binding.timer.apply {
            start(start, end)
            setOnTickListener {
                ((it / 1000).toInt()).toString()
            }
        }
        fragmentChangeByTimer()
    }
    private fun fragmentChangeByTimer(){
        Handler(Looper.getMainLooper()).postDelayed({
            checkEnd()
            setTimer()
        }, 9000)
    }
}