package com.example.cinemaddict.domain.usecase.home

import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.domain.repository.HomeRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.TrendingResponse
import javax.inject.Inject

class TrendingUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    suspend operator fun invoke(page: Int): ApiResponse<MovPagingWrapper<TrendingResponse>> =
        repository.getTrending(page)
}