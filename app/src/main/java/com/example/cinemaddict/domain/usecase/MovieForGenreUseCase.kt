package com.example.cinemaddict.domain.usecase

import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.entity.SortedByTypes
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import javax.inject.Inject

class MovieForGenreUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun getMovieForGenre(
        genre: String,
        page: Int,
        sortedBy: String = SortedByTypes.POPULARITY_DESC.value
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>> {
        return movieRepository.getMoviesForGenre(
            genre = genre,
            page = page,
            sortBy = sortedBy
        )
    }
}