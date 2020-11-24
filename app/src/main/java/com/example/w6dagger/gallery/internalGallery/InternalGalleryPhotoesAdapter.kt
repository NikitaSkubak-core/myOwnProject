package com.example.w6dagger.gallery.internalGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.databinding.ItemGalleryBinding
import java.io.File

class InternalGalleryPhotoesAdapter :
    RecyclerView.Adapter<InternalGalleryPhotoesAdapter.ImageViewHolder>() {

    private val listOfPictures by lazy { MutableLiveData<List<String>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = listOfPictures.value!![position]
        holder.bind(image)
    }

    override fun getItemCount(): Int {
        return listOfPictures.value!!.size
    }

    class ImageViewHolder(var binding: ItemGalleryBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(bindItem: String) {
            binding.content = bindItem
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGalleryBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }
    }

    fun getImageAtPosition(position: Int): File {
        return File(listOfPictures.value!![position])
    }

    fun setListOfPictures(images: List<File>) {
        for (image in images) listOfPictures.value = convertToListOfPaths(images)
        notifyDataSetChanged()
    }

    private fun convertToListOfPaths(images: List<File>): List<String> {
        return images.map { image -> image.absolutePath }
    }
}