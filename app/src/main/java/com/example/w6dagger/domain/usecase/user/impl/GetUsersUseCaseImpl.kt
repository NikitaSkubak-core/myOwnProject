package com.example.w6dagger.domain.usecase.user.impl

import com.example.w6dagger.dataBase.User
import com.example.w6dagger.domain.usecase.user.GetUsersUseCase
import com.example.w6dagger.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetUsersUseCaseImpl @Inject constructor(private val repository: UserRepository) :
    GetUsersUseCase {

    override fun getAllUsers(): Single<List<User>> =
        repository
            .getUsers()
            .subscribeOn(Schedulers.io())

    override fun deleteAllUsers(): Completable =
        repository
            .deleteAllUsers()
            .subscribeOn(Schedulers.io())

    override fun deleteUser(user: User): Completable =
        repository
            .deleteUser(user)
            .subscribeOn(Schedulers.io())


    override fun insertUser(user: User): Completable =
        repository
            .insertUser(user)
            .subscribeOn(Schedulers.io())

}