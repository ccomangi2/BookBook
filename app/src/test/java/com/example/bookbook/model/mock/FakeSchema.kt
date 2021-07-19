package com.example.bookbook.model.mock

import com.example.bookbook.model.schema.book.BookRequest

object FakeSchema {
    val mockBookSchema = BookRequest(
        query = "test",
    )

    val mockBookSchema0 = BookRequest(
        "test0",
    )
    val mockBookSchema1 = BookRequest(
        "test1",
    )
    val mockBookSchema2 = BookRequest(
        "test2",
    )
}