package com.example.cinemaddict.ui.home

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemaddict.common.paging.MovPagingSource
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.domain.usecase.home.TrendingUseCase
import com.example.cinemaddict.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingUseCase: TrendingUseCase
) : BaseViewModel() {

    val trendingMovieList: Flow<PagingData<TrendingMovieData>> = Pager(
        config = PagingConfig(pageSize = PAGE_SIZE),
        pagingSourceFactory = {
            MovPagingSource { page ->
                trendingUseCase(page)
            }
        }
    ).flow
        .cachedIn(viewModelScope)

    private companion object {
        const val PAGE_SIZE = 20
    }
}