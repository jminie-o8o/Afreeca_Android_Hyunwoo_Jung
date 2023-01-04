package com.example.afreecaandroid.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.ActivityMainBinding
import com.example.afreecaandroid.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        bindTabLayoutAtFragment()
    }

    private fun bindTabLayoutAtFragment() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "토크/캠방"
                1 -> tab.text = "여행"
                2 -> tab.text = "먹방/쿡방"
            }
        }.attach()
    }
}
