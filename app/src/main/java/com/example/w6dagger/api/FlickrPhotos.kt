package com.example.w6dagger.api

import java.util.ArrayList

class FlickrPhotos(
    val page: Int,
    val pages: String,
    val perpage: Int,
    val total: String,
    val photo: ArrayList<FlickrPhoto>,
    val stat: String
)