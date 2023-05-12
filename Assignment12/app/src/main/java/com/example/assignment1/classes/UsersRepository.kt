package com.example.assignment1.classes

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UsersRepository(private val userDao: UserDao) {
    val allUsers = MutableLiveData<List<User>>()
    val foundUser = MutableLiveData<User>()


    fun getAllUsers(){
        allUsers.postValue(userDao.getAllUsers())
    }

    suspend fun getUser(email: String) {
        return foundUser.postValue(userDao.getUserByEmail(email))
    }

    suspend fun insertUser(user: User){
        userDao.insert(user)

    }

//    suspend fun deleteUser(user: User){
//        userDao.delete(user)
//    }
//
//    suspend fun updateUser(user: User){
//        userDao.update(user)
//    }
}