package com.example.cinemaddict.common.paging

fun <IN : MovPagingResponse, OUT : MovPagingData> MovPagingResponseWrapper<IN>.toDataWrapper(
    transform: (List<IN>) -> List<OUT>
): MovPagingDataWrapper<OUT> =
    MovPagingDataWrapper(
        page = page,
        results = transform.invoke(results),
        totalPages = totalPages,
        totalResults = totalResults
    )