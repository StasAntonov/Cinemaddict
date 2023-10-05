package com.example.cinemaddict.repository

import com.example.cinemaddict.api.MovieApi
import com.example.cinemaddict.common.paging.MovPagingWrapper
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.network.BaseRepository
import com.example.cinemaddict.repository.response.GenreListResponse
import com.example.cinemaddict.repository.response.MovieResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : BaseRepository(), MovieRepository {

    override suspend fun getGenre(): ApiResponse<GenreListResponse> {
        return request { movieApi.getGenre() }
    }

    override suspend fun getMoviesForGenre(
        page: Int,
        genre: String,
        sortBy: String
    ): ApiResponse<MovPagingWrapper<MovieResponse>> {
        return request {
            movieApi.getMoviesForGenre(
                page = page,
                genre = genre,
                sortBy = sortBy
            )
        }
    }

}