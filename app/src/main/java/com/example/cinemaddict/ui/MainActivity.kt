package com.example.cinemaddict.ui

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ActivityMainBinding
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseUiActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    override fun initViews() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_main) as NavHostFragment
        bnvNavigation.setupWithNavController(navHostFragment.navController)

        bnvNavigation.itemIconTintList = null

        super.initViews()
    }

    override fun initObservers() {
        viewModel.isNetworkAvailable.observe(this) {
            if (it) {
                toast("Connect")
            } else {
                toast("No internet")
            }
        }
    }
}