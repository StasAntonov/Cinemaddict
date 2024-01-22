package com.example.cinemaddict.ui.discover

import androidx.fragment.app.viewModels
import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.adapter.GenrePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    private lateinit var adapter: GenrePagerAdapter

    override val viewModel: DiscoverViewModel by viewModels()

    override fun initListeners() {
        super.initListeners()
        onRefresh(Dispatchers.Main) {
            viewModel.refresh()
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.genre.observe(viewLifecycleOwner) {
            adapter = GenrePagerAdapter(childFragmentManager, lifecycle)
            adapter.setGenres(it)
            binding.vpPager.adapter = adapter

            binding.vpPager.offscreenPageLimit = 1
            TabLayoutMediator(
                binding.tlGenre,
                binding.vpPager,
                adapter
            ).attach()
        }

        viewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

}