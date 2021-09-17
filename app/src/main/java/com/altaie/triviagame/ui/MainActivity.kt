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

    override fun setup() {
        initViewPager()
        initTabLayout()
    }

    private fun initViewPager() {
        val fragmentsList = listOf(HomeFragment(this), ChallengeFragment(), ResultFragment())
        binding.viewPager.adapter = ViewPagerAdapter(this, fragmentsList = fragmentsList)
    }

    override fun callBack() {

    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private fun initTabLayout() {
        val tabIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_sports,
            R.drawable.ic_baseline_bar_chart_24,
        ).map(this::getDrawable)
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.icon = tabIcons[position]
            }.attach()
        }
    }

    override fun onClickItem(name: String) {
        DataManager.category = name
        binding.viewPager.currentItem = 1
    }

//    private fun loadImage(url: String) {
//        Glide.with(this).load(url)
//            .placeholder(R.drawable.ic_baseline_downloading_24)
//            .error(R.drawable.ic_baseline_error_outline_24)
//            .into(binding.imageView)
//    }

}