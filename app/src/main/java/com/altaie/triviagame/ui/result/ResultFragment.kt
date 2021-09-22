package com.altaie.triviagame.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.altaie.triviagame.R
import com.altaie.triviagame.databinding.FragmentResultBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.util.Constant


class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val result = it.getString(Constant.RESULT_KEY)
            binding.result.text = "$result\\100"
            result?.let { it1 -> resultStatus(it1.toInt()) }
        }

    }
    private fun resultStatus(result: Int){
        when {
            result >= 90 -> {
                binding.resultStatus.text = "Excellent"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.dark_green))
            }
            result >= 80 -> {
                binding.resultStatus.text = "Very Good"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.purple_700))
            }
            result >= 70 -> {
                binding.resultStatus.text = "Good"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.purple_500))
            }
            result >= 60 -> {
                binding.resultStatus.text = "Medium"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.blue))
            }
            result >= 50 -> {
                binding.resultStatus.text = "Passable"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.darkGrey))
            }
            else -> {
                binding.resultStatus.text = "Weak"
                binding.resultStatus.setTextColor(getColor(requireContext(), R.color.dark_red))
            }
        }
    }

    override fun setup() {}

    override fun callBack() {}

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate
}