package com.example.flickpick.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.flickpick.data.local.FlickPickDatabase
import com.example.flickpick.data.local.entity.MovieEntity
import com.example.flickpick.data.local.entity.RemoteKeyEntity
import com.example.flickpick.data.mapper.toEntity
import com.example.flickpick.data.model.MovieResponseDto
import retrofit2.HttpException
import java.io.IOException

/**
 * RemoteMediator that fetches pages from TMDB and stores them in Room.
 *
 * @param category Identifies the list type (e.g. "popular", "top_rated", "genre_28").
 * @param database The Room database for transactional writes.
 * @param fetchPage Lambda that fetches a page of movies from the network.
 */
@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val category: String,
    private val database: FlickPickDatabase,
    private val fetchPage: suspend (page: Int) -> MovieResponseDto
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = database.movieDao()
    private val remoteKeyDao = database.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.getRemoteKey(category)
                    remoteKey?.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = fetchPage(page)
            val endReached = page >= response.totalPages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearByCategory(category)
                    remoteKeyDao.clearByCategory(category)
                }

                val entities = response.results.map { it.toEntity(category, page) }
                movieDao.insertAll(entities)
                remoteKeyDao.insertOrReplace(
                    RemoteKeyEntity(
                        category = category,
                        nextPage = if (endReached) null else page + 1
                    )
                )
            }

            MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
