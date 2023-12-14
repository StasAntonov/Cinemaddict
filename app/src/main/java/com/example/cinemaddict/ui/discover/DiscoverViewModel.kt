package com.example.cinemaddict.ui.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cinemaddict.common.paging.MovPagingSource
import com.example.cinemaddict.domain.entity.FilmDiscoverData
import com.example.cinemaddict.domain.entity.GenreData
import com.example.cinemaddict.domain.mapper.toGenreData
import com.example.cinemaddict.domain.usecase.GenreUseCase
import com.example.cinemaddict.domain.usecase.MovieForTitleUseCase
import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase,
    private val movieUseCase: MovieForTitleUseCase
) : BaseViewModel() {

    private var _genre = MutableLiveData<List<GenreData>>()
    val genre: LiveData<List<GenreData>> = _genre

    private var _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    val searchText = MutableLiveData<String>()

    private var lastQuery: String = ""

    private val _dataFilm = MutableStateFlow<PagingData<FilmDiscoverData>>(PagingData.empty())
    val dataFilm: StateFlow<PagingData<FilmDiscoverData>> get() = _dataFilm

    init {
        getGenres()
    }

    fun getMovie(title: String) {
        if (title != "") {
            viewModelScope.launch {
                Pager(
                    config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        MovPagingSource { page ->
                            movieUseCase.getMovieForTitle(
                                page = page,
                                query = title
                            )
                        }
                    }
                ).flow
                    .cachedIn(viewModelScope)
                    .collectLatest { newData ->
                        _dataFilm.value = newData
                    }
            }
        } else {
            _dataFilm.value = PagingData.empty()
        }
        lastQuery = title
    }

    private fun getGenres() {
        viewModelScope.launch {

            genreUseCase.getGenres().let {
                when (it) {
                    is ApiResponse.Error -> {
                        _error.postValue(it.throwable.message.toString())
                    }

                    is ApiResponse.Success -> {
                        _genre.postValue(it.data.genres.map { g -> g.toGenreData() })
                    }
                }
            }
        }

    }

    fun refresh() {
        getGenres()
        if (lastQuery.isNotEmpty()) {
            getMovie(lastQuery)
        }
    }

}