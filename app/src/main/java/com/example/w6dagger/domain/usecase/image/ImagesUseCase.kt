package com.example.w6dagger.domain.usecase.image

import com.example.w6dagger.dataBase.Image
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ImagesUseCase {
    fun getRequestsOfUser(username: String): Single<List<String>>
    fun getImagesOfRequest(username: String, request: String): Single<List<Image>>
    fun getFavoriteImagesOfUser(username: String): Single<List<Image>>
    fun deleteImage(image: Image): Completable
    fun deleteRequests(username: String): Completable
    fun deleteRequest(username: String, request: String): Completable
    fun insertImage(username: String, request: String): Observable<Long>
    fun updateImage(image: Image): Single<Int>
}