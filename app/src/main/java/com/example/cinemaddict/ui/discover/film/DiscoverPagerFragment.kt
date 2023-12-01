package com.example.cinemaddict.ui.discover.film

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.databinding.FragmentFilmBinding
import com.example.cinemaddict.databinding.ItemDiscoverScreenBinding
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.mapper.toFilmDiscoverData
import com.example.cinemaddict.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverPagerFragment : BaseFragment<FragmentFilmBinding>(FragmentFilmBinding::inflate) {

    private var genre: String? = null
    private val discoverPagerViewModel: DiscoverPagerViewModel by viewModels()

    private val filmAdapter: MovPagingAdapter<FilmDiscoverData, ItemDiscoverScreenBinding> by lazy {
        MovPagingAdapter(ItemDiscoverScreenBinding::inflate)
    }

    private val staggeredGridLayoutManager: StaggeredGridLayoutManager by lazy {
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun initViews() {
        super.initViews()
        genre = arguments?.getString(ARG_GENRE)

        binding.rvList.apply {
            genre?.let {
                layoutManager = staggeredGridLayoutManager
                adapter = filmAdapter
                discoverPagerViewModel.setGenre(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            discoverPagerViewModel.dataFilm.collectLatest { list ->
                filmAdapter.submitData(list.map { g -> g.toFilmDiscoverData() })
            }
        }

    }

    companion object {
        private const val ARG_GENRE = "genreId"
        fun newInstance(genre: String): DiscoverPagerFragment {
            val fragment = DiscoverPagerFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_GENRE, genre)
            }

            return fragment
        }
    }

}