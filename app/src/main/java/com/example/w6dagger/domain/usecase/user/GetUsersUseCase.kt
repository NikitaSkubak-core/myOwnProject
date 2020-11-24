package com.example.w6dagger.domain.usecase.user

import com.example.w6dagger.dataBase.User
import io.reactivex.Completable
import io.reactivex.Single

interface GetUsersUseCase {
    fun getAllUsers(): Single<List<User>>
    fun deleteAllUsers(): Completable
    fun deleteUser(user: User): Completable
    fun insertUser(user: User): Completable
}