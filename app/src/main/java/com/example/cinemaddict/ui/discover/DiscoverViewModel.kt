package com.example.cinemaddict.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaddict.domain.entity.GenreData
import com.example.cinemaddict.domain.mapper.toGenreData
import com.example.cinemaddict.domain.usecase.GetGenreUseCase
import com.example.cinemaddict.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getGenreUseCase: GetGenreUseCase
) : ViewModel() {

    val genre = MutableLiveData<List<GenreData>>()
    val error = MutableLiveData<String>()

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch {

            getGenreUseCase.getGenres().let {
                when (it) {
                    is ApiResponse.Error -> {
                        error.postValue(it.throwable.message.toString())
                    }

                    is ApiResponse.Success -> {
                        genre.postValue(it.data.genres.map { g -> g.toGenreData() })
                    }
                }
            }
        }

    }

}