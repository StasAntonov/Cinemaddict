package com.example.cinemaddict.api

import com.example.cinemaddict.api.ApiEndpoint.GET_TRENDING
import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.repository.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @GET(GET_TRENDING)
    suspend fun getTrendingMovies(
        @Path(TimeWindow.NAME) timeWindow: String = TimeWindow.DAY,
        @Query(Pagination.PAGE) page: Int = 1,
        @Query(ApiQuery.LANGUAGE) language: String = "en-US"
    ): Response<MovPagingWrapper<TrendingResponse>>
}