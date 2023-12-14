package com.example.cinemaddict.domain.repository

import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.GenreListResponse

interface MovieRepository {
    suspend fun getGenre(): ApiResponse<GenreListResponse>

    suspend fun getMoviesForGenre(
        page: Int = 1,
        genre: String,
        sortBy: String
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>>

    suspend fun getMovieForTitle(
        page: Int = 1,
        query: String
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>>
}