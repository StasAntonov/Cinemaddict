package com.example.cinemaddict.domain.entity

import com.example.cinemaddict.common.paging.MovPagingData
import java.time.LocalDate

data class FilmDiscoverData(
    val title: String,
    val releaseDate: LocalDate?,
    val poster: String? = null
) : MovPagingData()
