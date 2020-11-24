package com.example.w6dagger.dataBase.dao

import androidx.room.*
import com.example.w6dagger.dataBase.User
import io.reactivex.Single

@Dao
abstract class UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertUser(user: User?)

    @Query("SELECT * from User")
    abstract fun getAllUsers(): Single<List<User>>

    @Query("DELETE from User")
    abstract fun deleteAllUsers()

    @Delete
    abstract fun deleteUser(user: User?)
}