package com.example.assignment1.classes

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application){

    private val userList : LiveData<List<User>>
    private val usersRepository: UsersRepository

    init {
        val userDao = UserRoomDatabase.getDatabase(application).userDao()
        usersRepository = UsersRepository(userDao)
        userList = usersRepository.allUsers

    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.insertUser(user)
        }
    }

    fun findUserByEmail(email:String) : MutableLiveData<User> {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getUser(email)
        }
        return usersRepository.foundUser
    }

    fun getAllUsers(){
        usersRepository.getAllUsers()
        println("here")
        var userList = usersRepository.allUsers
        userList.value?.forEach {
            println(it.email + " " + it.password)
        }
    }



}

