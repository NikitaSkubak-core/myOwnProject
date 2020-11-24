package com.example.w6dagger.util

import com.example.w6dagger.api.FlickrPhoto
import com.example.w6dagger.dataBase.Image

fun FlickrPhoto.toImage(username: String, request: String, link: String): Image {
    return Image(
        userName = username,
        request = request,
        response = link,
        favorite = false
    )
}