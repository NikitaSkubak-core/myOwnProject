package com.example.w6dagger.gallery.internalGallery

import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.R
import com.example.w6dagger.camera.CameraActivity
import com.example.w6dagger.databinding.ActivityGalleryBinding
import com.example.w6dagger.main.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.content_gallery.*
import java.io.File
import java.util.*
import javax.inject.Inject

class InternalGalleryActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var mGalleriesImageModel: InternalGalleryActivityModel
    lateinit var adapter: InternalGalleryPhotoesAdapter
    lateinit var binding: ActivityGalleryBinding
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        context = this
        binding.fab.setOnClickListener(clickListener)

        mGalleriesImageModel = injectViewModel(viewModelFactory)

        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setPermissions(
                permission.READ_EXTERNAL_STORAGE,
                permission.WRITE_EXTERNAL_STORAGE,
                permission.MANAGE_EXTERNAL_STORAGE
            )
            .check()

        adapter = InternalGalleryPhotoesAdapter()
        mGalleriesImageModel.imagesData.observe(this, observer)
        binding.contentGallery.recyclerviewGallery.adapter = adapter

        itemTouchHelper(adapter).attachToRecyclerView(recyclerview_gallery)
    }

    private val permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            Toast.makeText(context, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun itemTouchHelper(adapter: InternalGalleryPhotoesAdapter): ItemTouchHelper {
        return ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val image = adapter.getImageAtPosition(position)
                    Toast.makeText(
                        this@InternalGalleryActivity,
                        "Deleting image" + image.name,
                        Toast.LENGTH_LONG
                    ).show()
                    mGalleriesImageModel.deleteImage(image)
                }
            })
    }

    private val observer: Observer<List<File>> =
        Observer { images ->
            adapter.setListOfPictures(images)
        }
    var clickListener = View.OnClickListener { view ->
        Snackbar.make(view, "make new photo action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        val makingPhotoIntent = Intent(context, CameraActivity::class.java)
        context.startActivity(makingPhotoIntent)
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    fun changeTheme(view: View) {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        recreate()
    }
}