package com.example.bookbook.model.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bookbook.base.arch.util.converter.BigDecimalTypeConverter
import com.example.bookbook.base.arch.util.converter.StringListTypeConverter
import com.example.bookbook.base.constant.AppConfig
import com.example.bookbook.model.data.source.local.book.BookDao
import com.example.bookbook.model.schema.book.BookItem

/**
 * Main cache description.
 */
@Database(
    entities = [BookItem::class],
    exportSchema = false,
    version = AppConfig.roomVersionCode
)

@TypeConverters(BigDecimalTypeConverter::class, StringListTypeConverter::class)
abstract class Cache : RoomDatabase() {
    abstract fun bookDao(): BookDao
}