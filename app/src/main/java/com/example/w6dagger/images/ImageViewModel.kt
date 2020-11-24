package com.example.w6dagger.images

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.w6dagger.dataBase.Image
import com.example.w6dagger.repository.ImageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ImageViewModel @Inject constructor(private val repository: ImageRepository) : ViewModel() {
    val imageData by lazy { MutableLiveData<List<Image>>() }
    private val disposable = CompositeDisposable()
    fun getImageData(user: String, request: String) {

        disposable
            .add(
                repository.getImagesOfRequest(user, request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { imageData.value = it },
                        Throwable::printStackTrace
                    )
            )

    }

    fun updateImage(image: Image) {
        disposable
            .add(
                repository.updateImage(image)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(System.out::println, Throwable::printStackTrace)
            )
    }

    fun deleteImage(image: Image) {
        disposable
            .add(
                repository.deleteImage(image)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {},
                        Throwable::printStackTrace
                    )
            )
    }

    fun getFavoriteImages(user: String) {
        disposable
            .add(
                repository.getFavoriteImagesOfUser(user)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { imageData.value = it },
                        Throwable::printStackTrace
                    )
            )
    }
}