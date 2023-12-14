package com.example.cinemaddict.domain.usecase

import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import javax.inject.Inject

class MovieForTitleUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovieForTitle(
        page: Int,
        query: String
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>> {
        return movieRepository.getMovieForTitle(
            page = page,
            query = query
        )
    }
}