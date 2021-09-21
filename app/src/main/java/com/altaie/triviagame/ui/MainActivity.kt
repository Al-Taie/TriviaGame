package com.altaie.triviagame.ui

import android.view.LayoutInflater
import com.altaie.triviagame.R
import com.altaie.triviagame.data.DataManager
import com.altaie.triviagame.databinding.ActivityMainBinding
import com.altaie.triviagame.ui.base.BaseActivity
import com.altaie.triviagame.ui.challenge.ChallengeFragment
import com.altaie.triviagame.ui.home.HomeFragment
import com.altaie.triviagame.ui.interfaces.ItemListener
import com.altaie.triviagame.ui.result.ResultFragment
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : BaseActivity<ActivityMainBinding>(), ItemListener {
    override val theme: Int
        get() = R.style.Theme_TriviaGame

    override fun setup() {}

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onClickItem(name: String) {
        DataManager.category = name
    }

}