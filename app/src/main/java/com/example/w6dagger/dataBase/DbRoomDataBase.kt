package com.example.w6dagger.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.w6dagger.dataBase.dao.ImageDao
import com.example.w6dagger.dataBase.dao.UserDao

@Database(entities = [User::class, Image::class], version = 6, exportSchema = false)
abstract class DbRoomDataBase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getImageDao(): ImageDao

    companion object {
        private var INSTANCE: DbRoomDataBase? = null
        fun getDatabase(context: Context): DbRoomDataBase? {
            if (INSTANCE == null) {
                synchronized(DbRoomDataBase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DbRoomDataBase::class.java, "word_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}