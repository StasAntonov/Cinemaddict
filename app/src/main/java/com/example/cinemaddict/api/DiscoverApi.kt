package com.example.cinemaddict.api

import com.example.cinemaddict.common.paging.MovPagingResponseWrapper
import com.example.cinemaddict.repository.response.GenreListResponse
import com.example.cinemaddict.repository.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET(ApiEndpoint.GET_GENRES)
    suspend fun getGenre(): Response<GenreListResponse>

    @GET(ApiEndpoint.GET_MOVIE_FOR_GENRES)
    suspend fun getMoviesForGenre(
        @Query(Pagination.PAGE) page: Int,
        @Query(ApiQuery.SORTED) sortBy: String,
        @Query(ApiQuery.WITH_GENRES) genre: String
    ): Response<MovPagingResponseWrapper<MovieResponse>>

}