package com.example.cinemaddict.ui.home

import androidx.fragment.app.viewModels
import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private var count: Int = 0

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
//        lifecycleScope.launch {
//            showLoader() // todo need to show progress
//            delay(5000) // todo some operation
//            hideLoader() // todo need to hide progress
//        }

        // todo example how to implement refresh
        onRefresh {
            delay(5000)
            viewModel.refresh(count)
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.count.observe(viewLifecycleOwner) {
            count = it.toInt()
        }
    }

    override fun initListeners() {
        super.initListeners()
        binding.btnDetails.setOnClickListener {
            navigate(HomeFragmentDirections.showDetails())
        }
    }
}