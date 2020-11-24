package com.example.w6dagger.repository

import com.example.w6dagger.api.FlickrPhoto
import com.example.w6dagger.dataBase.Image
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ImageRepository {
    /**
     * Returns the requests list of current user.
     * @return list of requests, list would be empty if user has not made any request yet.
     * @param username String to set the name of user into SQL query.
     **/
    fun getRequestsOfUser(username: String): Single<List<String>>

    /**
     * Returns list of responses from dataBase which made by user and request that
     * user did before.
     * @return list of Images, list would be empty if dataBase`s table Image hadn`t links to an
     * image.
     * @param username String to set the name of user into SQL query.
     * @param request String to set the text of request into SQL query.
     **/
    fun getImagesOfRequest(username: String, request: String): Single<List<Image>>

    /**
     * Returns the list of Images that were marked as 'favorite' in table Image of dataBase
     * (value of field 'favorite' is 1 in Image table).
     * @return list of Images, list would be empty if user has not marked any image from any requests.
     * @param username String to set the name of user into SQL query.
     **/
    fun getFavoriteImagesOfUser(username: String): Single<List<Image>>

    /**
     * Deletes current image from dataBase.
     * @param image Image to set the datas of object Image into SQL query.
     **/
    fun deleteImage(image: Image): Completable

    /**
     * Deletes all requests of user from dataBase.
     * @param username String to set the name of user into SQL query which deletes all requests.
     **/
    fun deleteRequests(username: String): Completable

    /**
     * Deletes request and all images that were connected with request of user from dataBase.
     * @param username String to set the name of user into SQL query which deletes request.
     * @param request String to set the text of request into SQL query which deletes this request.
     **/
    fun deleteRequest(username: String, request: String): Completable

    /**
     * Inserts list of links to image into Image table of dataBase.
     * @param image Image to set image into SQL dataBase.
     **/
    fun insertImage(image: Image): Observable<Long>

    /**
     * Updates datas of Image in Image Table from dataBase.
     * @param image Image to set the new data Of Image into SQL query which updates data In DB.
     **/
    fun updateImage(image: Image): Single<Int>

    fun searchImagesInFlickr(request: String): Single<List<FlickrPhoto>>


}