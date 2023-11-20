package com.example.mobile_p12.tugas

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import com.example.mobile_p12.tugas.auth.User
import com.example.mobile_p12.tugas.auth.UserDao
import com.example.mobile_p12.tugas.homepage.Buku
import com.example.mobile_p12.tugas.homepage.BukuDao

@Database(entities = [User::class, Buku::class],
    version = 3,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun UserDao(): UserDao?
    abstract fun BukuDao() : BukuDao?

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "perpustakaan_database"
                    )
                        .addMigrations(migration2to3)
                        .build()
                }
            }
            return INSTANCE
        }

        private val migration2to3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `buku_table` " +
                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`buku` TEXT NOT NULL, " +
                            "`penulis` TEXT NOT NULL, " +
                            "`genre` TEXT NOT NULL, " +
                            "`harga` INTEGER NOT NULL)"
                )
            }
        }
    }
}
