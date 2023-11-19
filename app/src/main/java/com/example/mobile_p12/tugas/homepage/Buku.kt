package com.example.mobile_p12.tugas.homepage

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buku_table")
data class Buku (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id:Int = 0,

    @ColumnInfo(name = "buku")
    val buku: String,

    @ColumnInfo(name = "penulis")
    val penulis: String,

    @ColumnInfo(name = "genre")
    val genre: String,

    @ColumnInfo(name = "harga")
    val harga : String

)