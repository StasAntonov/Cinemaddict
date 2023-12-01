package com.example.cinemaddict.ui.discover

import androidx.fragment.app.viewModels
import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.adapter.GenrePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    private lateinit var adapter: GenrePagerAdapter

    private val discoverViewModel: DiscoverViewModel by viewModels()

    override fun initViews() {
        super.initViews()
        adapter = GenrePagerAdapter(childFragmentManager, lifecycle)
    }

    override fun initObservers() {
        super.initObservers()
        discoverViewModel.genre.observe(viewLifecycleOwner) {
            adapter.setGenres(it)
            binding.vpPager.adapter = adapter

            binding.vpPager.offscreenPageLimit = 1
            TabLayoutMediator(
                binding.tlGenre,
                binding.vpPager,
                adapter
            ).attach()
        }

        discoverViewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

}