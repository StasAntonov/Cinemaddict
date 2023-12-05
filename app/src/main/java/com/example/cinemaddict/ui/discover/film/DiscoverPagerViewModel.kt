package com.example.cinemaddict.ui.discover.film

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemaddict.common.paging.MovPagingSource
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.usecase.MovieForGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DiscoverPagerViewModel @Inject constructor(
    private val movieForGenreUseCase: MovieForGenreUseCase
) : ViewModel() {

    private var genre: String? = null

    val dataFilm: Flow<PagingData<FilmDiscoverData>> by lazy {
        loadData()
    }

    private fun loadData(): Flow<PagingData<FilmDiscoverData>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                MovPagingSource { page ->
                    movieForGenreUseCase.getMovieForGenre(genre.orEmpty(), page)
                }
            }
        ).flow.cachedIn(viewModelScope)
    }

    fun setGenre(genre: String) {
        this.genre = genre
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

}