package com.example.w6dagger.repository.impl

import com.example.w6dagger.dataBase.dao.UserDao
import com.example.w6dagger.dataBase.User
import com.example.w6dagger.repository.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val dao: UserDao) : UserRepository {

    override fun deleteAllUsers() = Completable.fromAction { dao.deleteAllUsers() }

    override fun deleteUser(user: User) = Completable.fromAction { dao.deleteUser(user) }

    override fun insertUser(user: User) = Completable.fromAction { dao.insertUser(user) }

    override fun getUsers() = dao.getAllUsers()
}
