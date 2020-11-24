package com.example.w6dagger.camera

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.w6dagger.R
import com.example.w6dagger.databinding.ActivityCameraBinding
import com.example.w6dagger.images.BitmapData
import com.example.w6dagger.main.ViewModelProviderFactory
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.yalantis.ucrop.UCrop
import dagger.android.support.DaggerAppCompatActivity
import java.io.File
import java.util.*
import javax.inject.Inject

class CameraActivity : DaggerAppCompatActivity() {
    private val context: Context = this

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    lateinit var editViewModel: EditPictureActivityModel
    private val CAMERA_REQUEST = 1
    private var DESTINATION_FOR_SAVE = 0
    private lateinit var binding: ActivityCameraBinding
    lateinit var file: File
    private val disc: File
        get() {
            val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            return File(file, "My Image")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)
        editViewModel = injectViewModel(viewModelProviderFactory)
        file = disc
        val bool = intent.getStringExtra("data") != null
        if (bool) {
            val bitmapData = BitmapData.bitmap
            binding.pictureView.setImageBitmap(bitmapData)
            DESTINATION_FOR_SAVE = 1
        }
    }

    private fun editPhoto(sourceUri: Uri) {
        UCrop.of(sourceUri, sourceUri)
            .withAspectRatio(16f, 9f)
            .withMaxResultSize(binding.pictureView.width, binding.pictureView.height)
            .start(this)
    }

    private val picture: Unit
        get() {
            TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(
                    permission.CAMERA,
                    permission.READ_EXTERNAL_STORAGE,
                    permission.WRITE_EXTERNAL_STORAGE,
                    permission.MANAGE_EXTERNAL_STORAGE
                )
                .check()
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    var permissionlistener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            val bitmap = data!!.extras!!["data"] as Bitmap
            binding.pictureView.setImageBitmap(bitmap)
            DESTINATION_FOR_SAVE = 2
        }
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            binding.pictureView.setImageURI(resultUri)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.e("Unexpected exception", "exception" + cropError!!.message)
        }
    }

    fun browseImageListener(view: View) {
        binding.pictureView.buildDrawingCache()
        val bitmap = binding.pictureView.drawingCache
        val uriForUcrop = editViewModel.saveImage(bitmap, DESTINATION_FOR_SAVE)
        editPhoto(uriForUcrop)
    }

    fun makePhoto(view: View) {
        picture
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }
}