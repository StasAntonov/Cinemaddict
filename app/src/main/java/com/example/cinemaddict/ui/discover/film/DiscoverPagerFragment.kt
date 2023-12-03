package com.example.cinemaddict.ui.discover.film

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.databinding.FragmentFilmBinding
import com.example.cinemaddict.databinding.ItemDiscoverScreenBinding
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.DiscoverFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverPagerFragment : BaseUiFragment<FragmentFilmBinding>(FragmentFilmBinding::inflate) {

    private var genre: String? = null
    private val discoverPagerViewModel: DiscoverPagerViewModel by viewModels()

    private val filmAdapter: MovPagingAdapter<FilmDiscoverData, ItemDiscoverScreenBinding> by lazy {
        MovPagingAdapter(
            bindingInflater = ItemDiscoverScreenBinding::inflate,
            onClickListener = ::navigateToDetailScreen
        )
    }

    override fun initViews() {
        super.initViews()
        genre = arguments?.getString(ARG_GENRE)

        binding.rvList.apply {
            genre?.let {
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = filmAdapter
                discoverPagerViewModel.setGenre(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            discoverPagerViewModel.dataFilm.collectLatest { list ->
                filmAdapter.submitData(list)
            }
        }
    }

    private fun navigateToDetailScreen(position: Int) {
        filmAdapter.getItemByPosition(position)?.let {
            navigate(DiscoverFragmentDirections.showDetails())
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