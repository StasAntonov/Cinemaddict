package com.example.cinemaddict.domain.usecase.home

import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.common.paging.MovPagingResponseWrapper
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.domain.repository.HomeRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.TrendingResponse
import javax.inject.Inject

class TrendingUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend operator fun invoke(page: Int): ApiResponse<MovPagingDataWrapper<TrendingMovieData>> =
        repository.getTrending(page)
}