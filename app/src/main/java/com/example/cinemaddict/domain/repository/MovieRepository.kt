package com.example.cinemaddict.domain.repository

import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.GenreListResponse
import com.example.cinemaddict.repository.response.MovieResponse

interface MovieRepository {
    suspend fun getGenre(): ApiResponse<GenreListResponse>

    suspend fun getMoviesForGenre(
        page: Int = 1,
        genre: String,
        sortBy: String = "popularity.desc",
    ): ApiResponse<MovPagingWrapper<MovieResponse>>
}