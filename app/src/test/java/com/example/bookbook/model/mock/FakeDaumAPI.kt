package com.example.bookbook.model.mock

import com.example.bookbook.model.data.source.api.DaumAPI
import com.example.bookbook.model.schema.book.BookResponse

class FakeDaumAPI : DaumAPI {
    override suspend fun getBookListSus(
        query: String,
        size: Int?,
        page: Int?
    ): BookResponse {
        return FakeModel.mockResponseModel
    }
}