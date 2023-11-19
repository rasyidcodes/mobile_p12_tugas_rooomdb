package com.example.mobile_p12.tugas

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import  androidx.room.Room.databaseBuilder
import com.example.mobile_p12.tugas.auth.User
import com.example.mobile_p12.tugas.auth.UserDao


@Database(entities = [User::class],
    version = 2,
    exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun UserDao() : UserDao?

    companion object {
        @Volatile
        private var INSTANCE : AppDatabase ? = null
        fun getDatabase(context: Context) : AppDatabase?{
            if(INSTANCE == null){
                synchronized(AppDatabase::class.java){
                    INSTANCE = databaseBuilder(context.applicationContext, AppDatabase::class.java, "perpustakaan_database").build()
                }
            }
            return  INSTANCE
        }
    }
}