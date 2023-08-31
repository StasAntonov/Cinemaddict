package com.example.cinemaddict.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ActivityMainBinding
import com.example.cinemaddict.ui.base.BaseUiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseUiActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_main) as NavHostFragment
        bnvNavigation.setupWithNavController(navHostFragment.navController)

        bnvNavigation.itemIconTintList = null
    }
}