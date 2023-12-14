package com.example.cinemaddict.ui.discover

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.databinding.FragmentDiscoverBinding
import com.example.cinemaddict.databinding.ItemDiscoverScreenBinding
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.ext.toast
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.ui.discover.adapter.GenrePagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DiscoverFragment : BaseUiFragment<FragmentDiscoverBinding>(FragmentDiscoverBinding::inflate) {

    private lateinit var adapter: GenrePagerAdapter
    override val viewModel: DiscoverViewModel by viewModels()
    private val movieAdapter: MovPagingAdapter<FilmDiscoverData, ItemDiscoverScreenBinding> by lazy {
        MovPagingAdapter(
            bindingInflater = ItemDiscoverScreenBinding::inflate,
            onClickListener = ::navigateToDetailScreen
        )
    }

    override fun initViews() {
        super.initViews()
        binding.viewModel = viewModel
        binding.rvList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = movieAdapter
        }
    }

    override fun initListeners() {
        super.initListeners()
        onRefresh(Dispatchers.Main) {
            viewModel.refresh()
        }
        movieAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                showLoader()
            } else {
                hideLoader()
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

    @OptIn(FlowPreview::class)
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

        viewModel.searchText.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                isGetMovie(true)
                viewModel.getMovie(it)
            } else {
                isGetMovie(false)
                viewModel.getMovie("")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.dataFilm.debounce(300)
                .distinctUntilChanged()
                .collectLatest { list ->
                    movieAdapter.submitData(list)
                }
        }
    }

    private fun isGetMovie(isGet: Boolean) {
        binding.apply {
            if (isGet) {
                tlGenre.visibility = View.GONE
                pullToRefresh.visibility = View.GONE
                rvList.visibility = View.VISIBLE
            } else {
                rvList.visibility = View.GONE
                tlGenre.visibility = View.VISIBLE
                pullToRefresh.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToDetailScreen(position: Int) {
        movieAdapter.getItemByPosition(position)?.let {
            navigate(DiscoverFragmentDirections.showDetails())
        }
    }
}