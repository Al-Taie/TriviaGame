package com.altaie.triviagame.ui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import com.altaie.triviagame.databinding.FragmentResultBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.util.Constant


class ResultFragment : BaseFragment<FragmentResultBinding>() {

    override fun onStart() {
        super.onStart()
        arguments?.let {
            val result = it.getString(Constant.RESULT_KEY)
            binding.result.text = "$result\\100"
        }
    }

    override fun setup() {}

    override fun callBack() {}

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentResultBinding
        get() = FragmentResultBinding::inflate
}