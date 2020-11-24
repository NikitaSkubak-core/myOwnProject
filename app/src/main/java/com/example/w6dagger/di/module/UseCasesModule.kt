package com.example.w6dagger.di.module

import com.example.w6dagger.domain.usecase.image.ImagesUseCase
import com.example.w6dagger.domain.usecase.image.impl.ImagesUseCaseImpl
import com.example.w6dagger.domain.usecase.user.GetUsersUseCase
import com.example.w6dagger.domain.usecase.user.impl.GetUsersUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {
    @Binds
    fun bindUsersUseCase(useCase: GetUsersUseCaseImpl): GetUsersUseCase

    @Binds
    fun bindImageUseCase(useCase: ImagesUseCaseImpl): ImagesUseCase
}