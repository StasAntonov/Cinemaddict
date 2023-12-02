package com.example.cinemaddict.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cinemaddict.network.ApiResponse

private typealias PagingCallback<T> = suspend (Int) -> ApiResponse<MovPagingDataWrapper<T>>

class MovPagingSource<T : MovPagingData>(
    private val callback: PagingCallback<T>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: INIT_PAGE

        return when (val result = callback(page)) {
            is ApiResponse.Success -> {
                val nextPage = if (result.data.results.isNotEmpty()) {
                    if (page >= result.data.totalPages) null else page + 1
                } else null

                LoadResult.Page(
                    data = result.data.results,
                    prevKey = if (page <= INIT_PAGE) null else page - 1,
                    nextKey = nextPage
                )
            }

            is ApiResponse.Error -> LoadResult.Error(result.throwable)
        }
    }

    private companion object {
        const val INIT_PAGE = 1
    }
}