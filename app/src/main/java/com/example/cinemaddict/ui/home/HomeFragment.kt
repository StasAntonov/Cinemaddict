package com.example.cinemaddict.ui.home

import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun initViews() {
        super.initViews()
//        lifecycleScope.launch {
//            showLoader() // todo need to show progress
//            delay(5000) // todo some operation
//            hideLoader() // todo need to hide progress
//        }

        // todo example how to implement refresh
        onRefresh {
            delay(5000)
            toast("Finish")
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.btnDetails.setOnClickListener {
            navigate(HomeFragmentDirections.showDetails())
        }
    }
}