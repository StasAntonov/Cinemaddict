package com.example.cinemaddict.domain.usecase

import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.MovieResponse
import javax.inject.Inject

class MovieForGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovieForGenre(
        genre: String,
        page: Int = 1
    ): ApiResponse<MovPagingWrapper<MovieResponse>> {
        return movieRepository.getMoviesForGenre(genre = genre, page = page)
    }
}