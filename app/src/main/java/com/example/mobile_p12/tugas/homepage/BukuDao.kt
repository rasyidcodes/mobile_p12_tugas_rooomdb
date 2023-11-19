package com.example.mobile_p12.tugas.homepage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mobile_p12.tugas.auth.User

@Dao
interface BukuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(buku: Buku)
    @Update
    fun update(buku: Buku)

    @Delete
    fun delete(buku: Buku)

}