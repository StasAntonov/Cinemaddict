package com.example.cinemaddict.ui.discover.film

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cinemaddict.common.paging.MovPagingSource
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.usecase.MovieForGenreUseCase
import com.example.cinemaddict.repository.response.MovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiscoverPagerViewModel @Inject constructor(
    private val movieForGenreUseCase: MovieForGenreUseCase
) : ViewModel() {

    fun getMoviesPaging(genre: String): Flow<PagingData<FilmDiscoverData>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MovPagingSource { page ->
                    movieForGenreUseCase.getMovieForGenre(genre = genre, page)
                }
            }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}