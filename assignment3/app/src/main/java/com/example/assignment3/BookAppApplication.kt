package com.example.assignment3

import android.app.Application
import com.example.assignment3.di.AppContainer
import com.example.assignment3.di.DefaultAppContainer

class BookAppApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}