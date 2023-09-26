package com.example.cinemaddict.domain.repository

import com.example.cinemaddict.network.ApiResponse
import com.example.cinemaddict.repository.response.GenreListResponse

interface MovieRepository {
    suspend fun getGenre(): ApiResponse<GenreListResponse>
}