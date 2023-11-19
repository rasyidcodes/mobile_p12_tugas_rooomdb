package com.example.mobile_p12

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import  androidx.room.Room.databaseBuilder


@Database(entities = [Note::class],
    version = 1,
    exportSchema = false)

abstract class NoteRoomDatabase : RoomDatabase() {
    abstract fun NoteDao() : NoteDao?

    companion object {
        @Volatile
        private var INSTANCE : NoteRoomDatabase ? = null
        fun getDatabase(context: Context) : NoteRoomDatabase?{
            if(INSTANCE == null){
                synchronized(NoteRoomDatabase::class.java){
                    INSTANCE = databaseBuilder(context.applicationContext, NoteRoomDatabase::class.java, "note_database").build()
                }
            }
            return  INSTANCE
        }
    }
}