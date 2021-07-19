package com.example.bookbook.model.mock

import androidx.paging.PagingSource
import com.example.bookbook.model.data.source.local.book.BookDao
import com.example.bookbook.model.schema.book.BookItem
import org.mockito.Mockito

class FakeRoom : BookDao {
    override fun getBooksPagingSource(): PagingSource<Int, BookItem> {
        return mockPagedList()
    }

    override suspend fun clear() {
    }

    override suspend fun insert(vararg obj: BookItem) {
    }

    override suspend fun insert(obj: BookItem) {
    }

    override suspend fun insert(obj: MutableList<BookItem>) {
    }

    override suspend fun insert(obj: ArrayList<BookItem>) {
    }

    override suspend fun delete(obj: BookItem) {
    }

    override suspend fun update(vararg obj: BookItem) {
    }

    override suspend fun update(obj: BookItem) {
    }

    override suspend fun update(obj: MutableList<BookItem>) {
    }

    fun mockPagedList(): PagingSource<Int, BookItem> {
        val pagingSource = Mockito.mock(PagingSource::class.java) as PagingSource<Int, BookItem>
//        Mockito.`when`(pagedList.get(ArgumentMatchers.anyInt())).then { invocation ->
//            val index = invocation.arguments.first() as Int
//            list[index]
//        }
//        Mockito.`when`(pagedList.size).thenReturn(list.size)
        return pagingSource
    }
}