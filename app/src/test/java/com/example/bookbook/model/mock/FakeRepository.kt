package com.example.bookbook.model.mock

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.example.bookbook.model.data.source.repository.book.BooksRepository
import com.example.bookbook.model.mock.FakeModel.mockPagingData
import com.example.bookbook.model.schema.book.BookItem
import com.example.bookbook.model.schema.book.BookRequest
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FakeRepository : BooksRepository(FakeCache(), FakeDaumAPI()) {
    @OptIn(ExperimentalPagingApi::class)
    override fun execute(bookSchema: BookRequest): Flow<PagingData<BookItem>> {

        return flow {
            emit(mockPagingData)
            awaitFrame()
        }
    }

    override fun clearCache() = runBlocking { FakeCache().bookDao().clear() }
}