package com.example.assignment3.di

import com.example.assignment3.data.BookshelfRepository
import com.example.assignment3.network.BookshelfApiService

interface AppContainer {

    val bookshelfApiService: BookshelfApiService
    val bookshelfRepository: BookshelfRepository
}