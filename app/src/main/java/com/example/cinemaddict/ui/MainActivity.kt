package com.example.cinemaddict.ui

import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.cinemaddict.R
import com.example.cinemaddict.databinding.ActivityMainBinding
import com.example.cinemaddict.ui.base.BaseUiActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.Stack

@AndroidEntryPoint
class MainActivity : BaseUiActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()

    private var isLaunchApp: Boolean = true

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_container_main) as NavHostFragment).navController
    }
    private val startDestinationID: Int by lazy { navController.graph.startDestinationId }

    private val fragmentBackStack: Stack<Int> by lazy { Stack<Int>().apply { push(startDestinationID) } }

    override fun initViews() = with(binding) {
        bnvNavigation.itemIconTintList = null

        super.initViews()
    }

    override fun initObservers() {
        super.initObservers()
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
        super.iniListeners()

        binding.bnvNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home,
                R.id.discover,
                R.id.profile -> {
                    pushToBackStack(it.itemId)
                    itemSelectNavigation(it.itemId)
                }

                else -> false
            }
        }
    }

    private fun itemSelectNavigation(fragment: Int): Boolean {
        navController.let {
            return if (it.currentDestination?.id != fragment) {
                it.popBackStack(fragment, true)
                it.navigate(fragment)
                true
            } else {
                false
            }
        }
    }

    private fun pushToBackStack(fragmentId: Int): Boolean {
        if (fragmentId == fragmentBackStack.lastOrNull()) {
            return false
        }

        if (!fragmentBackStack.contains(fragmentId)) {
            fragmentBackStack.push(fragmentId)
        } else {
            if (fragmentId == startDestinationID) {
                if (fragmentBackStack.count { it == startDestinationID } < 2) {
                    fragmentBackStack.push(fragmentId)
                } else {
                    fragmentBackStack.asReversed().remove(fragmentId)
                    fragmentBackStack.push(fragmentId)
                }
            } else {
                fragmentBackStack.remove(fragmentId)
                fragmentBackStack.push(fragmentId)
                if (fragmentBackStack[0] == fragmentBackStack[1]) {
                    fragmentBackStack.removeFirst()
                }
            }
        }
        return true
    }

    override fun onBackListener() {
        if (fragmentBackStack.size > 1) {
            fragmentBackStack.pop()
            val fragmentId = fragmentBackStack.lastElement()
            binding.bnvNavigation.selectedItemId = fragmentId
        } else if (fragmentBackStack.size == 1) {
            finish()
        }
    }
}