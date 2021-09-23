package com.altaie.triviagame.ui.challenge

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
import com.altaie.triviagame.util.Index
import com.kofigyan.stateprogressbar.StateProgressBar
import java.util.*


class ChallengeFragment : BaseFragment<FragmentChallengeBinding>() {
    override fun setup() {}

    override fun callBack() {
        bindData()
        setOptions()
        setTimer()
        countDown()
        binding.progress.apply {
            checkStateCompleted(true)
            setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
        }
    }

    lateinit var countDownTimer: CountDownTimer

    private fun setOptions() {
        binding.optionsGroup.children.forEach { button ->
            button.setOnClickListener {
                val correctAnswer = TrivialRepository.currentQuestion.correctAnswer
                val currentAnswer = (button as AppCompatButton).text
                if (correctAnswer == currentAnswer) {
                    TrivialRepository.score += 10
                }
                checkEnd()
                countDownTimer.cancel()
                countDownTimer.start()
            }
        }
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentChallengeBinding
        get() = FragmentChallengeBinding::inflate

    private fun checkEnd() {
        setTimer()
        val bundle = Bundle()
        val fragment = ResultFragment()
        val position = TrivialRepository.position
        val result = TrivialRepository.score.toString()

        bundle.putString(Constant.RESULT_KEY, result)
        fragment.arguments = bundle

        if (position < TrivialRepository.lastPosition) {
            TrivialRepository.position++
            bindData()
        } else {
            with(Index) {
                TrivialRepository.apply {
                    this.position = ZERO
                    progressState = ONE
                    score = ZERO

                }
                replaceFragment(fragment = fragment)
            }

            binding.progress.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
        }

        updateProgressBar()
    }

    private fun updateProgressBar() {
        val position = TrivialRepository.position
        if (position % 3 == 0) {
            TrivialRepository.progressState++
            val progressState = when (TrivialRepository.progressState) {
                Index.ONE -> StateProgressBar.StateNumber.ONE
                Index.TWO -> StateProgressBar.StateNumber.TWO
                Index.THREE -> StateProgressBar.StateNumber.THREE
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
            val list = mutableListOf<String>().apply {
                addAll(incorrectAnswers)
                add(correctAnswer)
                shuffle()
            }

            binding.question.text = question

            binding.apply {
                optionOne.text = list[Index.ZERO]
                optionTwo.text = list[Index.ONE]
                optionThree.text = list[Index.TWO]
                optionFour.text = list[Index.THREE]
            }
        }
    }

    private fun setTimer() {
        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        start.add(Calendar.SECOND, 0)
        end.add(Calendar.SECOND, 10)

        binding.timer.apply {
            this.start(start, end)
            setOnTickListener { it.div(1000).toString() }
        }
    }

    private fun countDown() {
        countDownTimer = object : CountDownTimer(9000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if (TrivialRepository.position != TrivialRepository.lastPosition) {
                    countDownTimer.start()
                }
                checkEnd()
            }
        }
    }
}