package com.example.w6dagger.api

class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
) {

    override fun toString() =
        "https://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + ".jpg"
}