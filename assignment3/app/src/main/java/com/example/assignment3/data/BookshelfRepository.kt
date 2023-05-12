package com.example.assignment3.data

import com.example.assignment3.Model.Book

interface BookshelfRepository {

    suspend fun getBooks(query:String):List<Book>?
    suspend fun getBook(id: String): Book?
}