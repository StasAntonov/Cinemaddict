package com.example.cinemaddict.domain.usecase

import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.GenreListResponse
import javax.inject.Inject

class GetGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getGenres(): ApiResponse<GenreListResponse> {
        return movieRepository.getGenre()
    }
}