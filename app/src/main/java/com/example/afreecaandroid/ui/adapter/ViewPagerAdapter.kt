package com.example.afreecaandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.afreecaandroid.ui.view.CookFragment
import com.example.afreecaandroid.ui.view.TalkCamFragment
import com.example.afreecaandroid.ui.view.TravelFragment

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    companion object {
        const val NUM_PAGE = 3
    }

    override fun getItemCount(): Int = NUM_PAGE

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TalkCamFragment()
            1 -> TravelFragment()
            else -> CookFragment()
        }
    }
}
