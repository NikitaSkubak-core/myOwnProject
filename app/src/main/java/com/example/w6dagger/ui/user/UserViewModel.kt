package com.example.w6dagger.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w6dagger.dataBase.User
import com.example.w6dagger.domain.usecase.user.GetUsersUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import javax.inject.Inject

class UserViewModel @Inject constructor(private val userUseCase: GetUsersUseCase) : ViewModel() {
    val allUsers by lazy { MutableLiveData<List<User>>() }
    private val disposable = CompositeDisposable()

    fun getAllUsers() {
        disposable.add(
            userUseCase.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    allUsers.value = it
                }, Throwable::printStackTrace)
        )
    }

    fun deleteAllUsers() {
        disposable.add(
            userUseCase.deleteAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, Throwable::printStackTrace)
        )
    }

    fun deleteUser(user: User) {
        disposable.add(
            userUseCase.deleteUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, Throwable::printStackTrace)
        )
    }

    fun insertUser(user: User) {
        disposable.add(
            userUseCase.insertUser(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, Throwable::printStackTrace)
        )
    }
}