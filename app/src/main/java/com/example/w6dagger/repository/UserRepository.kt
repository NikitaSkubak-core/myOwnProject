package com.example.w6dagger.repository

import com.example.w6dagger.dataBase.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    /**
     * Deletes all users from dataBase.
     **/
    fun deleteAllUsers(): Completable

    /**
     * Deletes user from dataBase.
     * @param user User that would be deleted from dataBase
     **/
    fun deleteUser(user: User): Completable

    /**
     * Inserts a new user into dataBase.
     * @param user User that would be inserted into dataBase
     **/
    fun insertUser(user: User): Completable

    fun getUsers(): Single<List<User>>
}