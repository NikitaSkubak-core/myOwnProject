package com.example.w6dagger.request

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w6dagger.domain.usecase.image.ImagesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RequestViewModel @Inject constructor(private val repository: ImagesUseCase) : ViewModel() {
    val requests by lazy { MutableLiveData<List<String>>() }
    private val disposable = CompositeDisposable()
    fun getRequestOfUser(user: String) {
        disposable
            .add(
                repository.getRequestsOfUser(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { requests.value = it as List<String>? },
                        Throwable::printStackTrace
                    )
            )
    }

    fun deleteRequest(username: String, request: String) {
        disposable.add(
            repository.deleteRequest(username, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun insertRequest(username: String, request: String) {
        disposable.add(
            repository.insertImage(username, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, Throwable::printStackTrace)
        )
    }

    fun deleteRequests(username: String) {
        disposable.add(
            repository.deleteRequests(username)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }
}