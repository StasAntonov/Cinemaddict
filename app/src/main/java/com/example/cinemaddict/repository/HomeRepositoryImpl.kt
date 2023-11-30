package com.example.cinemaddict.repository

import com.example.cinemaddict.api.HomeApi
import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.domain.repository.HomeRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.network.BaseRepository
import com.example.cinemaddict.repository.response.TrendingResponse
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : BaseRepository(), HomeRepository {

    override suspend fun getTrending(page: Int): ApiResponse<MovPagingWrapper<TrendingResponse>> =
        request { api.getTrendingMovies(page = page) }
}