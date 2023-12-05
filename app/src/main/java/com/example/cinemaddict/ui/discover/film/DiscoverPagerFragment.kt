package com.example.cinemaddict.ui.discover.film

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.databinding.FragmentDiscoverPagerBinding
import com.example.cinemaddict.databinding.ItemDiscoverScreenBinding
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.DiscoverFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverPagerFragment :
    BaseUiFragment<FragmentDiscoverPagerBinding>(FragmentDiscoverPagerBinding::inflate) {

    private var genre: String? = null
    private val discoverPagerViewModel: DiscoverPagerViewModel by viewModels()

    private val filmAdapter: MovPagingAdapter<FilmDiscoverData, ItemDiscoverScreenBinding> by lazy {
        MovPagingAdapter(
            bindingInflater = ItemDiscoverScreenBinding::inflate,
            onClickListener = ::navigateToDetailScreen
        )
    }

    override fun initListeners() {
        super.initListeners()
        filmAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                binding.progress.showProgress()
            } else {
                binding.progress.hideProgress()
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    toast(it.error.toString())
                }
            }
        }
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