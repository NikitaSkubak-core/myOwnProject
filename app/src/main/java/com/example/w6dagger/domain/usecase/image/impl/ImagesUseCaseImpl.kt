package com.example.w6dagger.domain.usecase.image.impl

import com.example.w6dagger.dataBase.Image
import com.example.w6dagger.domain.usecase.image.ImagesUseCase
import com.example.w6dagger.repository.ImageRepository
import com.example.w6dagger.util.toImage
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImagesUseCaseImpl @Inject constructor(private val repository: ImageRepository) :
    ImagesUseCase {
    override fun getRequestsOfUser(username: String): Single<List<String>> =
        repository
            .getRequestsOfUser(username)
            .subscribeOn(Schedulers.io())

    override fun getImagesOfRequest(username: String, request: String): Single<List<Image>> =
        repository
            .getImagesOfRequest(username, request)
            .subscribeOn(Schedulers.io())


    override fun getFavoriteImagesOfUser(username: String): Single<List<Image>> =
        repository
            .getFavoriteImagesOfUser(username)
            .subscribeOn(Schedulers.io())


    override fun deleteImage(image: Image): Completable =
        repository
            .deleteImage(image)
            .subscribeOn(Schedulers.io())


    override fun deleteRequests(username: String): Completable =
        repository
            .deleteRequests(username)
            .subscribeOn(Schedulers.io())

    override fun deleteRequest(username: String, request: String): Completable =
        repository
            .deleteRequest(username, request)
            .subscribeOn(Schedulers.io())

    override fun insertImage(username: String, request: String): Observable<Long> =
        repository
            .searchImagesInFlickr(request)
            .toObservable()
            .flatMapIterable { it }
            .flatMap {
                repository
                    .insertImage(
                        it.toImage(
                            username,
                            request,
                            it.toString()
                        )
                    )
            }.subscribeOn(Schedulers.io())


    override fun updateImage(image: Image): Single<Int> =
        repository
            .updateImage(image)
            .subscribeOn(Schedulers.io())
}