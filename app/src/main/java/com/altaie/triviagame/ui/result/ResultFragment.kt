package com.altaie.triviagame.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.altaie.triviagame.R
import com.altaie.triviagame.databinding.FragmentResultBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.home.HomeFragment
import com.altaie.triviagame.util.Constant


class ResultFragment : BaseFragment<FragmentResultBinding>() {
    lateinit var myHomeFragment: HomeFragment
    override fun onStart() {
        super.onStart()
        arguments?.let {
            val score = it.getString(Constant.RESULT_KEY)
            binding.result.text = "$score\\100"
            score?.let { it1 -> resultStatus(it1.toInt()) }
        }

    }
    private fun resultStatus(score: Int){
        binding.apply {
        when {
            score >= 90 -> {
                resultStatus.text = "Excellent"
                resultStatus.setTextColor(getColor(requireContext(), R.color.dark_green))
                result.setTextColor(getColor(requireContext(), R.color.dark_green))
            }
            score >= 80 -> {
                resultStatus.text = "Very Good"
                resultStatus.setTextColor(getColor(requireContext(), R.color.purple_700))
                result.setTextColor(getColor(requireContext(), R.color.purple_700))
            }
            score >= 70 -> {
                resultStatus.text = "Good"
                resultStatus.setTextColor(getColor(requireContext(), R.color.purple_500))
                result.setTextColor(getColor(requireContext(), R.color.purple_500))
            }
            score >= 60 -> {
                resultStatus.text = "Medium"
                resultStatus.setTextColor(getColor(requireContext(), R.color.blue))
                result.setTextColor(getColor(requireContext(), R.color.blue))
            }
            score >= 50 -> {
                resultStatus.text = "Passable"
                resultStatus.setTextColor(getColor(requireContext(), R.color.darkGrey))
                result.setTextColor(getColor(requireContext(), R.color.darkGrey))
            }
            else -> {
                resultStatus.text = "Weak"
                resultStatus.setTextColor(getColor(requireContext(), R.color.dark_red))
                result.setTextColor(getColor(requireContext(), R.color.dark_red))
            }
        }
        }
    }

    override fun setup() {}

    override fun callBack() {
        binding.returnHomeBtn.setOnClickListener {
            myHomeFragment = HomeFragment()
            replaceFragment(myHomeFragment)
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

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate
}