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
        sortBy: String = "popularity.desc",
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>>
}