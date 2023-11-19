package com.example.mobile_p12

import android.icu.text.CaseMap.Title
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NonNls

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id:Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description : String

)