package com.example.truecaller.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.truecaller.ui.fragments.CallsFragment
import com.example.truecaller.ui.fragments.ContactsFragment
import com.example.truecaller.ui.fragments.MessagesFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CallsFragment()
            1 -> MessagesFragment()
            2 -> ContactsFragment()
            else -> CallsFragment()
        }
    }
}
