package com.example.flickpick.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flickpick.data.mapper.toDomain
import com.example.flickpick.data.remote.ApiService
import com.example.flickpick.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

/**
 * PagingSource for search results.
 * Search is network-only (no Room caching) since queries are ephemeral.
 */
class SearchPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = apiService.searchMovies(query, page)
            val movies = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
