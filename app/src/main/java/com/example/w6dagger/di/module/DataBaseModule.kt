package com.example.w6dagger.di.module

import androidx.room.Room
import com.example.w6dagger.api.FlickrApi
import com.example.w6dagger.dataBase.DbRoomDataBase
import com.example.w6dagger.dataBase.dao.ImageDao
import com.example.w6dagger.dataBase.dao.UserDao
import com.example.w6dagger.di.MyApplication
import com.example.w6dagger.repository.ImageRepository
import com.example.w6dagger.repository.UserRepository
import com.example.w6dagger.repository.impl.ImageRepositoryImpl
import com.example.w6dagger.repository.impl.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: MyApplication): DbRoomDataBase {
        return Room.databaseBuilder(application, DbRoomDataBase::class.java, "dbName")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(db: DbRoomDataBase): UserDao = db.getUserDao()!!

    @Provides
    @Singleton
    fun provideImageDao(db: DbRoomDataBase): ImageDao = db.getImageDao()!!

    @Provides
    @Singleton
    fun provideUserRepository(dao: UserDao): UserRepository = UserRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideImageRepository(dao: ImageDao, flickr: FlickrApi): ImageRepository =
        ImageRepositoryImpl(dao, flickr)
}