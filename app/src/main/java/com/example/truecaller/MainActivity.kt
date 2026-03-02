package com.example.truecaller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.truecaller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_messages, menu)
        return true
    }

    private fun setupNavigation() {
        val adapter = MainViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        com.google.android.material.tabs.TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Calls"
                1 -> "Messages"
                2 -> "Contacts"
                else -> null
            }
        }.attach()
    }
}
