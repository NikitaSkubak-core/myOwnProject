package com.example.w6dagger.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("/services/rest/")
    fun getImages(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("tags") tags: String,
        @Query("per_page") perPage: Int,
        @Query("format") format: String,
        @Query("media") media: String,
        @Query("nojsoncallback") value: Int
    ): Single<FlickrResponse>
}