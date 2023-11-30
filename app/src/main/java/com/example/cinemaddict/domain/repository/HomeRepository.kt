package com.example.cinemaddict.domain.repository

import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.TrendingResponse

interface HomeRepository {

    suspend fun getTrending(page: Int): ApiResponse<MovPagingWrapper<TrendingResponse>>
}