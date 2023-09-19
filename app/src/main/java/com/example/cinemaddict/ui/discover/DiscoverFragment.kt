package com.example.cinemaddict.ui.discover

import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.adapter.GenrePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    override fun initViews() {
        super.initViews()
        //todo delete
        val genres = listOf("Комедия", "Драма-триллер", "Экшн", "Ужасы", "Детективы")

        val adapter = GenrePagerAdapter(childFragmentManager, lifecycle)
        binding.vpPager.adapter = adapter

        val tabLayoutMediator = TabLayoutMediator(
            binding.tlGenre,
            binding.vpPager,
            adapter
        )
        tabLayoutMediator.attach()
        adapter.setGenres(genres)
    }

}