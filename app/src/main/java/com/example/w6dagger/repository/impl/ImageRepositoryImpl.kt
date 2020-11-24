package com.example.w6dagger.repository.impl

import com.example.w6dagger.api.FlickrApi
import com.example.w6dagger.api.FlickrPhoto
import com.example.w6dagger.dataBase.Image
import com.example.w6dagger.dataBase.dao.ImageDao
import com.example.w6dagger.repository.ImageRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dao: ImageDao,
    private val flickr: FlickrApi
) : ImageRepository {

    override fun getRequestsOfUser(username: String): Single<List<String>> =
        dao.getRequests(username)

    override fun getImagesOfRequest(username: String, request: String): Single<List<Image>> =
        dao.getImagesOfRequest(username, request)

    override fun getFavoriteImagesOfUser(username: String): Single<List<Image>> =
        dao.getFavoriteImages(username)

    override fun updateImage(image: Image): Single<Int> = dao.updateImage(image)

    override fun deleteImage(image: Image) = Completable.fromAction { dao.deleteImage(image) }

    override fun deleteRequests(username: String): Completable =
        Completable.fromAction { dao.deleteRequests(username) }

    override fun deleteRequest(username: String, request: String): Completable =
        Completable.fromAction { dao.deleteRequest(username, request) }

    override fun insertImage(image: Image) = Observable.just(dao.insertImage(image))

    override fun searchImagesInFlickr(request: String): Single<List<FlickrPhoto>> {
        return flickr.getImages(
            method = "flickr.photos.search",
            apiKey = "f22d371a90671badec49b6e37ebc610e",
            tags = request,
            perPage = 25,
            format = "json",
            media = "photos",
            value = 1
        ).map { it.photos.photo }
    }
}