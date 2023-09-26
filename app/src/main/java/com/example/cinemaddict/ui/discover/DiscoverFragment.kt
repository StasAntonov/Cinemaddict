package com.example.cinemaddict.ui.discover

import androidx.fragment.app.viewModels
import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.domain.entity.GenreData
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.adapter.GenrePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    private lateinit var adapter: GenrePagerAdapter

    private lateinit var genres: List<GenreData>
    private val viewModel: DiscoverViewModel by viewModels()
    override fun initViews() {
        super.initViews()

        adapter = GenrePagerAdapter(childFragmentManager, lifecycle)
        binding.vpPager.adapter = adapter

        val tabLayoutMediator = TabLayoutMediator(
            binding.tlGenre,
            binding.vpPager,
            adapter
        )
        tabLayoutMediator.attach()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.genre.observe(viewLifecycleOwner) {
            genres = it
            adapter.setGenres(genres)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            toast(it)
        }
    }

}