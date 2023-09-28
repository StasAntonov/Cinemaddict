package com.example.cinemaddict.ui.discover

import androidx.fragment.app.viewModels
import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    override val viewModel: DiscoverViewModelImpl by viewModels()
    private var count: Int = 0

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
    }

    override fun initListeners() {
        super.initListeners()
        onRefresh {
            delay(4000)
            viewModel.refresh(count)
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.count.observe(viewLifecycleOwner) {
            count = it.toInt()
        }
    }
}