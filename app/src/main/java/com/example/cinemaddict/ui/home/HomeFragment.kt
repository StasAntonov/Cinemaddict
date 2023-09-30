package com.example.cinemaddict.ui.home

import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.domain.entity.LatestMovieData
import com.example.cinemaddict.ext.mainScope
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun initViews() = with(binding) {
        super.initViews()
//        lifecycleScope.launch {
//            showLoader() // todo need to show progress
//            delay(5000) // todo some operation
//            hideLoader() // todo need to hide progress
//        }

        // todo example how to implement refresh
//        onRefresh {
//            delay(5000)
//            viewModel.refresh(count)
//        }

        mainScope {
            incLatest.viewData =
                LatestMovieData(
                    "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                    "Мег 2: Бездна"
                )
        }

        Unit
    }

    override fun initListeners() {
        super.initListeners()
        binding.btnDetails.setOnClickListener {
            navigate(HomeFragmentDirections.showDetails())
        }
    }
}