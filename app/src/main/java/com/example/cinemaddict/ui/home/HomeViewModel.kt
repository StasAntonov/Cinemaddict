package com.example.cinemaddict.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cinemaddict.common.paging.MovPagingSource
import com.example.cinemaddict.domain.usecase.home.TrendingUseCase
import com.example.cinemaddict.repository.response.TrendingResponse
import com.example.cinemaddict.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingUseCase: TrendingUseCase
) : BaseViewModel() {

    val trendingMovieList: Flow<PagingData<TrendingResponse>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            MovPagingSource { page ->
                trendingUseCase(page)
            }
        }
    ).flow

    private companion object {
        const val PAGE_SIZE = 20
    }
}