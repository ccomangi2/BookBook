package com.example.bookbook.model.data.source.local.book

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.bookbook.model.schema.book.BookItem
import com.meuus90.booksearcher.model.data.source.local.BaseDao

@Dao
interface BookDao : BaseDao<BookItem> {
    @Query("SELECT * FROM Book")
    fun getBooksPagingSource(): PagingSource<Int, BookItem>

    @Query("DELETE FROM Book")
    suspend fun clear()
}