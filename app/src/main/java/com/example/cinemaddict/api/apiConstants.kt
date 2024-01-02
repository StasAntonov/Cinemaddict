package com.example.cinemaddict.api

object ApiEndpoint {
    const val GET_TRENDING = "trending/movie/{${TimeWindow.NAME}}"
    const val GET_GENRES = "genre/movie/list"
    const val GET_MOVIE_FOR_GENRES = "discover/movie"
    const val GET_MOVIE_FOR_TITLE = "search/movie"
}

object ApiQuery {
    const val QUERY = "query"
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