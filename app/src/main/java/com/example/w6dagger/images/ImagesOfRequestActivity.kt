package com.example.w6dagger.images

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.R
import com.example.w6dagger.dataBase.Image
import com.example.w6dagger.databinding.ActivityImagesOfRequestBinding
import com.example.w6dagger.main.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class ImagesOfRequestActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory
    lateinit var imagesViewModel: ImageViewModel

    private var adapter: ImagesListAdapter? = null

    lateinit var binding: ActivityImagesOfRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_images_of_request)

        setSupportActionBar(binding.appBar.toolbar)

        adapter = ImagesListAdapter()

        binding.contentImage.rvImage.adapter = adapter

        binding.contentImage.rvImage.layoutManager =
            GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        val datasOfRequest: List<String> = intent.getStringArrayListExtra(EXTRA_REPLY)!!

        imagesViewModel = injectViewModel(viewModelFactory)
        imagesViewModel.getImageData(datasOfRequest[0], datasOfRequest[1])
        imagesViewModel.imageData.observe(this, observer)

        itemTouchHelper(adapter!!).attachToRecyclerView(binding.contentImage.rvImage)
    }

    private fun itemTouchHelper(adapter: ImagesListAdapter): ItemTouchHelper {
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
                        this@ImagesOfRequestActivity,
                        "Deleting image",
                        Toast.LENGTH_LONG
                    ).show()
                    imagesViewModel.deleteImage(image)
                }
            })
    }

    private val observer: Observer<List<Image>> =
        Observer { images -> adapter!!.setImages(images) }

    companion object {
        const val EXTRA_REPLY = "com.example.android.w7dagger.REPLY"
    }

    private inline fun <reified T : ViewModel> injectViewModel(factory: ViewModelProvider.Factory): T {
        return ViewModelProviders.of(this, factory)[T::class.java]
    }

    fun changeTheme(view: View) {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()
    }
}