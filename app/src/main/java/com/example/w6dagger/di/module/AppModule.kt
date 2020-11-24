package com.example.w6dagger.di.module

import android.app.Application
import android.content.Context
import com.example.w6dagger.di.MyApplication
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Module(includes = [AndroidSupportInjectionModule::class, DataBaseModule::class, UseCasesModule::class, NetworkModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: MyApplication): Context = application.applicationContext

    @Singleton
    @Provides
    fun provideApplication(application: MyApplication): Application = application

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
}