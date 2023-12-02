package com.example.cinemaddict.ui.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaddict.common.paging.MovPagingAdapter
import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.databinding.ItemTrendingBinding
import com.example.cinemaddict.domain.entity.LatestMovieData
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.ui.base.BaseUiFragment
import com.example.cinemaddict.util.CarouselLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private val adapter: MovPagingAdapter<TrendingMovieData, ItemTrendingBinding> by lazy {
        MovPagingAdapter(
            bindingInflater = ItemTrendingBinding::inflate,
            onClickListener = ::navigateToDetailScreen
        )
    }

    private val loadStateListener: (CombinedLoadStates) -> Unit by lazy {
        {
            if (it.refresh is LoadState.NotLoading) {
                binding.rvList.scrollToPosition(0)
                adapter.removeLoadStateListener(loadStateListener)
            }
        }
    }

    override fun initViews() = with(binding) {
        super.initViews()

        onRefresh(Dispatchers.Main) {
            adapter.refresh()
            adapter.addLoadStateListener(loadStateListener)
        }

        incLatest.viewData =
            LatestMovieData(
                "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "Мег 2: Бездна"
            )

        rvList.layoutManager =
            CarouselLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        rvList.adapter = adapter
    }

    override fun initObservers() {
        super.initObservers()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.trendingMovieList.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun navigateToDetailScreen(position: Int) {
        adapter.getItemByPosition(position)?.let {
            navigate(HomeFragmentDirections.showDetails())
        }
    }
}