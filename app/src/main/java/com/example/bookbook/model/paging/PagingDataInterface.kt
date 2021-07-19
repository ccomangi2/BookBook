package com.example.bookbook.model.paging


interface PagingDataInterface<Request, Response> {
    fun execute(bookSchema: Request): Response
}