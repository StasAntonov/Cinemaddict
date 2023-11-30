package com.example.cinemaddict.repository

import com.example.cinemaddict.api.HomeApi
import com.example.cinemaddict.common.paging.MovPagingData
import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.common.paging.MovPagingResponseWrapper
import com.example.cinemaddict.common.paging.toDataWrapper
import com.example.cinemaddict.domain.entity.TrendingMovieData
import com.example.cinemaddict.domain.mapper.toTrendingData
import com.example.cinemaddict.domain.repository.HomeRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.network.BaseRepository
import com.example.cinemaddict.network.map
import com.example.cinemaddict.repository.response.TrendingResponse
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeApi
) : BaseRepository(), HomeRepository {

    override suspend fun getTrending(page: Int): ApiResponse<MovPagingDataWrapper<TrendingMovieData>> =
        request { api.getTrendingMovies(page = page) }
            .map { wrapper ->
                wrapper.toDataWrapper { responseList ->
                    responseList.map { it.toTrendingData() }
                }
            }
}