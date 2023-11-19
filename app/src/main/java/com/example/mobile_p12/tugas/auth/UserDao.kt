package com.example.mobile_p12.tugas.auth

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)
    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @get:Query("SELECT * FROM user_table ORDER BY id ASC")
    val allNotes : LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password")
    fun loginUser(email: String, password: String): User?

}