package com.example.cinemaddict.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemaddict.databinding.FragmentHomeBinding
import com.example.cinemaddict.databinding.ItemTrendingBinding
import com.example.cinemaddict.domain.entity.LatestMovieData
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.ui.base.BaseUiFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseUiFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val adapter: TrendingAdapter by lazy { TrendingAdapter() }

    override fun initViews() = with(binding) {
        super.initViews()
//        lifecycleScope.launch {
//            showLoader() // todo need to show progress
//            delay(5000) // todo some operation
//            hideLoader() // todo need to hide progress
//        }

        // todo example how to implement refresh
//        onRefresh {
//            delay(5000)
//            viewModel.refresh(count)
//        }

        incLatest.viewData =
            LatestMovieData(
                "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
                "Мег 2: Бездна"
            )

        rvList.adapter = adapter
        adapter.submitList(getTrendingList())

        Unit
    }

    private fun getTrendingList(): List<TrendingMovieData> = listOf(
        TrendingMovieData(
            "https://image.tmdb.org/t/p/w500/or06FN3Dka5tukK1e9sl16pB3iy.jpg",
            "Avengers",
            5f
        ),
        TrendingMovieData(
            "https://image.tmdb.org/t/p/w500/mXLOHHc1Zeuwsl4xYKjKh2280oL.jpg",
            "Harry Potter",
            25f
        ),
        TrendingMovieData(
            "https://image.tmdb.org/t/p/w500/53z2fXEKfnNg2uSOPss2unPBGX1.jpg",
            "Naruto",
            3f
        ),
        TrendingMovieData(
            "https://image.tmdb.org/t/p/w500/iOJX54nVAsnPawagFiWXKv1Y6sB.jpg",
            "Star wars",
            86f
        ),
        TrendingMovieData(
            "https://image.tmdb.org/t/p/w500/8Vt6mWEReuy4Of61Lnj5Xj704m8.jpg",
            "Spider-man",
            23f
        ),
    )
}

class TrendingAdapter : RecyclerView.Adapter<TrendingAdapter.ViewHolder>() {

    class ViewHolder(
        private val binding: ItemTrendingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TrendingMovieData) {
            binding.viewData = item
        }
    }

    private val trendingList = mutableListOf<TrendingMovieData>()

    fun submitList(list: List<TrendingMovieData>) {
        trendingList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = trendingList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trendingList[position])
    }
}