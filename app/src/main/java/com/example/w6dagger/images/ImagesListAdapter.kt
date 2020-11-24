package com.example.w6dagger.images

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.camera.CameraActivity
import com.example.w6dagger.dataBase.Image
import com.example.w6dagger.databinding.ItemImageBinding
import com.example.w6dagger.images.ImagesListAdapter.ImageViewHolder

class ImagesListAdapter : RecyclerView.Adapter<ImageViewHolder>() {
    private var images: List<Image> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val current = images[position]
        holder.bindDescription(current.response)
        holder.bindImage(current.response)
        holder.bindCheck(current.favorite)
        holder.bindRequest(current.request)
        holder.bindIdImage("" + current.id)
        holder.bindHint(current.userName)
    }

    fun setImages(images: List<Image>) {
        this.images = images
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mRequests has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder private constructor(val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        private val listenerUpdate = View.OnClickListener { v ->
            val id = binding.idImage.toInt()
            val user = binding.hintUserName
            val request = binding.request
            val response = binding.contentDescription
            val isChecked = !binding.check
            val image = Image(id, user, request, response, isChecked)
            (v.context as ImagesOfRequestActivity).imagesViewModel.updateImage(image)
        }
        private val clickListenerForImageView = View.OnClickListener { v ->
            val iv = v as ImageView
            iv.buildDrawingCache()
            BitmapData.bitmap = iv.drawingCache
            val intent = Intent(v.getContext(), CameraActivity::class.java)
            intent.putExtra("data", "getBitMap")

            v.getContext().startActivity(intent)
        }

        fun bindDescription(bindDescription: String?) {
            binding.contentDescription = bindDescription
        }

        fun bindImage(bindUrl: String?) {
            binding.imageOfFlickr = bindUrl
        }

        fun bindCheck(favorite: Boolean?) {
            binding.check = favorite!!
        }

        fun bindRequest(request: String?) {
            binding.request = request
        }

        fun bindIdImage(idImage: String?) {
            binding.idImage = idImage
        }

        fun bindHint(userName: String?) {
            binding.hintUserName = userName
        }

        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }

        init {
            binding.clickListenerUpdate = listenerUpdate
            binding.clickListener = clickListenerForImageView
        }
    }

    fun getImageAtPosition(position: Int): Image {
        return images[position]
    }
}