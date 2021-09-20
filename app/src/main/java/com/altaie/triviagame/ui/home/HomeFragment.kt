package com.altaie.triviagame.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.databinding.FragmentHomeBinding
import com.altaie.triviagame.ui.base.BaseFragment
import com.altaie.triviagame.ui.interfaces.ItemListener

class HomeFragment(private val listener: ItemListener) : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {
        binding.categoryRecycler.adapter = CategoryAdapter(DataManager.categories, listener)
    }

    override fun callBack() {}

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
}