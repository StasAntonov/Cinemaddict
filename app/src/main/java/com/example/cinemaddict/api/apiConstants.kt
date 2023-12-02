package com.example.cinemaddict.api

object ApiEndpoint {
    const val GET_TRENDING = "/3/trending/movie/{${TimeWindow.NAME}}"
    const val GET_GENRES = "3/genre/movie/list"
}

object ApiQuery {
    const val LANGUAGE = "language"
    const val SORTED = "sort_by"
    const val WITH_GENRES = "with_genres"
}

object TimeWindow {
    const val NAME = "time_window"

    const val DAY = "day"
    const val WEEK = "week"
}

object Pagination {
    const val PAGE = "page"
}