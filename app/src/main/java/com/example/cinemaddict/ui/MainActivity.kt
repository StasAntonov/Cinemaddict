package com.example.cinemaddict.ui

import android.os.Build
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ActivityMainBinding
import com.example.cinemaddict.ui.base.BaseUiActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseUiActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private var isLaunchApp: Boolean = true

    private lateinit var navHostFragment: NavHostFragment

    override fun initViews() = with(binding) {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_container_main) as NavHostFragment
        bnvNavigation.itemIconTintList = null

        super.initViews()
    }

    override fun initObservers() {
        viewModel.isNetworkAvailable.observe(this) {
            if (it && !isLaunchApp) {
                showSuccessMessage(
                    resources.getString(R.string.internet_connection_restored),
                    false
                )
            } else if (!it) {
                showErrorMessage(resources.getString(R.string.internet_no_connection), true)
                isLaunchApp = false
            }
        }
    }

    override fun iniListeners() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                onBack()
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBack()
                }
            })
        }

        binding.bnvNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home,
                R.id.discover,
                R.id.profile -> itemSelectNavigation(it.itemId)

                else -> {
                    false
                }
            }
        }
    }

    private fun itemSelectNavigation(fragment: Int): Boolean {
        navHostFragment.navController.let {
            return if (it.currentDestination?.id != fragment) {
                it.popBackStack(fragment, true)
                it.navigate(fragment)
                true
            } else {
                false
            }
        }
    }

    private fun onBack() {
        navHostFragment.navController.let {
            if (it.currentDestination?.id != R.id.home) {
                it.popBackStack(R.id.home, false)
                binding.bnvNavigation.menu.findItem(R.id.home).isChecked = true
            } else {
                finish()
            }
        }
    }
}