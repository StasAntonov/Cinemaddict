package com.example.cinemaddict.repository

import com.example.cinemaddict.api.MovieApi
import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.common.paging.MovPagingResponseWrapper
import com.example.cinemaddict.common.paging.toDataWrapper
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.mapper.toFilmDiscoverData
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.network.BaseRepository
import com.example.cinemaddict.network.map
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
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>> {
        return request {
            movieApi.getMoviesForGenre(
                page = page,
                genre = genre,
                sortBy = sortBy
            )
        }.map { wrapper ->
            wrapper.toDataWrapper { responseList ->
                responseList.map { it.toFilmDiscoverData() }
            }
        }
    }

}