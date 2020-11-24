package com.example.w6dagger.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.CodeBoy.MediaFacer.mediaHolders.pictureContent
import com.example.w6dagger.databinding.ItemGalleryBinding
import java.util.*

class GalleryImagesAdapter : RecyclerView.Adapter<GalleryImagesAdapter.ImageViewHolder>() {

    private val listOfPictures by lazy { MutableLiveData<List<String>>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = listOfPictures.value!![position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listOfPictures.value!!.size
    }

    fun getImageAtPosition(position: Int): String? {
        return listOfPictures.value!![position]
    }

    fun submitList(listOfString: List<String>) {
        listOfPictures.value = listOfString
    }

    class ImageViewHolder(var binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bindItem: String?) {
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

    companion object {
        fun getListOfString(listOfPictures: List<pictureContent>): List<String> {
            val listOfPicturesNames: MutableList<String> = ArrayList()
            for (picture in listOfPictures) listOfPicturesNames.add(picture.picturePath)
            return listOfPicturesNames
        }
    }
}