package com.example.assignment3.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment3.Model.Book
import com.example.assignment3.data.BookshelfRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ApiViewModel(): ViewModel() {

    var books: List<Book>? = null

    fun getBooks(bookshelfRepository: BookshelfRepository, query : String){
        viewModelScope.launch {
            try {
                books = bookshelfRepository.getBooks(query)
                if (books == null) {
                    println("error")
                } else if (books!!.isEmpty()){
                    println("empty")
                }
            } catch (e: IOException) {
                println("error")
            } catch (e: HttpException) {
                println("error")
            }
        }
    }

    fun loadBooks(bookshelfRepository: BookshelfRepository,query : String ) : List<Book>? {
        getBooks(bookshelfRepository, query)
        return books
    }
}