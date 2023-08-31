package com.example.cinemaddict.ui.home

import androidx.lifecycle.lifecycleScope
import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initViews() {
        lifecycleScope.launch {
            progress.showLoader() // todo need to show progress
            delay(5000) // todo some operation
            progress.hideLoader() // todo need to hide progress
        }
    }
}