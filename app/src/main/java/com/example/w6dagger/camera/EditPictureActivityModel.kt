package com.example.w6dagger.camera

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditPictureActivityModel @Inject constructor(application: Application) : ViewModel() {
    var DESTINATION_FLICKR = 1
    private val context: Context

    fun saveImage(bitmap_of_image: Bitmap, DESTINATION_FOR_IMAGE: Int): Uri {

        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat("yyyymmsshhmmss")
        val date = simpleDateFormat.format(Date())
        val name = "image$date.jpg"

        val dir = if (DESTINATION_FOR_IMAGE == DESTINATION_FLICKR) {
            File(Environment.getExternalStorageDirectory().absolutePath + "/Pictures/Flickr")
        } else
            File(context.filesDir.toString() + "/Pictures/AnImageForUCrop")

        dir.mkdirs()

        val fileToSave = File(dir, name)

        try {
            val fileForSave = FileOutputStream(fileToSave)
            bitmap_of_image.compress(Bitmap.CompressFormat.JPEG, 100, fileForSave)
            fileForSave.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        scanFile(context, Uri.fromFile(fileToSave))

        return Uri.fromFile(fileToSave)
    }

    private fun scanFile(context: Context, fromFile: Uri) {
        val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        scanIntent.data = fromFile
        context.sendBroadcast(scanIntent)
    }

    init {
        context = application
    }
}