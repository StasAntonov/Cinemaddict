package com.example.cinemaddict.domain.usecase

import com.example.cinemaddict.domain.entity.GenreList
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getGenres(): ApiResponse<GenreList> {
        return movieRepository.getGenre()
    }
}