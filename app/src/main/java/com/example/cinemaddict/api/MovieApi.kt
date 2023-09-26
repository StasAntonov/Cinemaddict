package com.example.cinemaddict.api

import com.example.cinemaddict.repository.response.GenreListResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("3/genre/movie/list")
    suspend fun getGenre(): Response<GenreListResponse>

}