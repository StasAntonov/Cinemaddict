package com.example.cinemaddict.domain.entity

import com.example.cinemaddict.common.paging.MovPagingData

data class FilmDiscoverData(
    val title: String,
    val releaseDate: String? = null,
    val poster: String? = null
) : MovPagingData()
