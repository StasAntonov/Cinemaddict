package com.example.cinemaddict.api

object ApiEndpoint {
    const val GET_TRENDING = "/3/trending/movie/{${TimeWindow.NAME}}"
}

object ApiQuery {
    const val LANGUAGE = "language"
}

object TimeWindow {
    const val NAME = "time_window"

    const val DAY = "day"
    const val WEEK = "week"
}

object Pagination {
    const val PAGE = "page"
}