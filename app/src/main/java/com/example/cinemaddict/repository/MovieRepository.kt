package com.example.cinemaddict.repository

import com.example.cinemaddict.api.MovieApi
import com.example.cinemaddict.network.BaseRepository
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieApi: MovieApi
) : BaseRepository() {

}