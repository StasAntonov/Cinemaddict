package com.example.cinemaddict.api

import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.repository.response.GenreListResponse
import com.example.cinemaddict.repository.response.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("3/genre/movie/list")
    suspend fun getGenre(): Response<GenreListResponse>

    @GET("3/discover/movie")
    suspend fun getMoviesForGenre(
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") genre: String
    ): Response<MovPagingWrapper<MovieResponse>>

}