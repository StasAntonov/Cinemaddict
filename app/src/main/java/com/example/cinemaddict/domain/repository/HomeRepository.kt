package com.example.cinemaddict.domain.repository

import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.common.paging.MovPagingResponseWrapper
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.TrendingResponse

interface HomeRepository {

    suspend fun getTrending(page: Int): ApiResponse<MovPagingDataWrapper<TrendingMovieData>>
}