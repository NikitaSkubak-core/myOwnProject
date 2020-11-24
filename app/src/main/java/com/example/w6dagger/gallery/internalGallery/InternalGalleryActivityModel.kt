package com.example.w6dagger.gallery.internalGallery

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*
import javax.inject.Inject

class InternalGalleryActivityModel @Inject constructor(application: Application) : ViewModel() {
    private val context: Context
    var imagesData: MutableLiveData<List<File>> = MutableLiveData()
    private fun getData(context: Context) {
        val files = File(context.filesDir.absolutePath + "/Pictures/AnImageForUCrop").listFiles()!!
        val listOfPictures: List<File> = ArrayList(listOf(*files))
        imagesData.value = listOfPictures
    }

    fun deleteImage(image: File) {
        image.delete()
    }

    init {
        context = application
        getData(application)
    }
}