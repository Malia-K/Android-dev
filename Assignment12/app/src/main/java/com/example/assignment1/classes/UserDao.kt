package com.example.assignment1.classes

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)


    @Query("SELECT * from users")
    fun getAllUsers(): List<User>


    @Query("SELECT * from users WHERE email LIKE :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User
}