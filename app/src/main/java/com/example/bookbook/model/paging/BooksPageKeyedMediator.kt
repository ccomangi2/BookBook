package com.example.bookbook.model.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.bookbook.base.arch.util.network.entity.NetworkError
import com.example.bookbook.base.constant.AppConfig
import com.example.bookbook.model.data.source.api.DaumAPI
import com.example.bookbook.model.data.source.local.Cache
import com.example.bookbook.model.data.source.local.book.BookDao
import com.example.bookbook.model.schema.book.BookItem
import com.example.bookbook.model.schema.book.BookRequest
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BooksPageKeyedMediator(
    private val db: Cache,
    private val daumAPI: DaumAPI,
    private val bookSchema: BookRequest
) : RemoteMediator<Int, BookItem>() {
    private val postDao: BookDao = db.bookDao()
    private var loadKey = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BookItem>
    ): MediatorResult {
        Timber.e("BooksPageKeyedMediator loadType : $loadType")
        Timber.e("BooksPageKeyedMediator loadKey : $loadKey")

        return try {
            loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    db.withTransaction {
                        postDao.clear()
                    }

                    1
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    loadKey + 1
                }
            }

            val response = daumAPI.getBookListSus(
                query = bookSchema.query,
                size = AppConfig.remotePagingSize,
                page = loadKey
            )

            if (response.meta.total_count == 0) {
                db.withTransaction {
                    postDao.clear()
                }
                return MediatorResult.Error(EmptyResultException())
            }

            db.withTransaction {
                postDao.insert(response.documents)
            }

            MediatorResult.Success(endOfPaginationReached = response.meta.is_end)
        } catch (e: IOException) {
            db.withTransaction {
                postDao.clear()
            }
            Timber.e(e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            db.withTransaction {
                postDao.clear()
            }
            Timber.e(e)
            MediatorResult.Error(parseToNetworkError(e))
        } catch (e: Exception) {
            db.withTransaction {
                postDao.clear()
            }
            Timber.e(e)
            MediatorResult.Error(e)
        }
    }

    private fun parseToNetworkError(e: HttpException): Exception {
        val errMsg = e.response()?.errorBody()?.string()
        errMsg?.let { message ->
            try {
                val networkError = Gson().fromJson(message, NetworkError::class.java)
                if (networkError.isMissingParameter())
                    return EmptyResultException()
            } catch (e: Exception) {

            }
        }
        return e
    }

    class EmptyResultException : Exception()
}
