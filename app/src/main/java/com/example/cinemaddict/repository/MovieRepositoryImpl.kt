package com.example.cinemaddict.repository

import com.example.cinemaddict.api.DiscoverApi
import com.example.cinemaddict.common.paging.MovPagingDataWrapper
import com.example.cinemaddict.common.paging.toDataWrapper
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.mapper.toFilmDiscoverData
import com.example.cinemaddict.domain.repository.MovieRepository
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.network.BaseRepository
import com.example.cinemaddict.network.map
import com.example.cinemaddict.repository.response.GenreListResponse
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val discoverApi: DiscoverApi
) : BaseRepository(), MovieRepository {

    override suspend fun getGenre(): ApiResponse<GenreListResponse> {
        return request { discoverApi.getGenre() }
    }

    override suspend fun getMoviesForGenre(
        page: Int,
        genre: String,
        sortBy: String
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>> =
        request {
            discoverApi.getMoviesForGenre(
                page = page,
                genre = genre,
                sortBy = sortBy
            )
        }.map { wrapper ->
            wrapper.toDataWrapper { responseList ->
                responseList.map { it.toFilmDiscoverData() }
            }
        }

    override suspend fun getMovieForTitle(
        page: Int,
        query: String
    ): ApiResponse<MovPagingDataWrapper<FilmDiscoverData>> =
        request {
            discoverApi.getMovieForTitle(
                page = page,
                query = query
            )
        }.map { wrapper ->
            wrapper.toDataWrapper { responseList ->
                responseList.map { it.toFilmDiscoverData() }
            }
        }

}