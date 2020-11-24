package com.example.w6dagger.gallery

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.CodeBoy.MediaFacer.MediaFacer
import com.CodeBoy.MediaFacer.PictureGet
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent
import java.io.File
import javax.inject.Inject

class GalleryActivityViewModel @Inject constructor(private val application: Application) :
    ViewModel() {

    val imagesData by lazy { MutableLiveData<List<pictureContent>>() }

    private fun getData(context: Context?) {
        imagesData.value = MediaFacer.withPictureContex(context)
            .getAllPictureContents(PictureGet.externalContentUri)
    }

    fun deleteImage(image: String) {
        val imageForDelete = File(image)
        imageForDelete.delete()
        val contentResolver = application.contentResolver
        contentResolver.delete(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            MediaStore.Images.ImageColumns.DATA + "=?", arrayOf(image)
        )
    }

    init {
        getData(application)
    }
}