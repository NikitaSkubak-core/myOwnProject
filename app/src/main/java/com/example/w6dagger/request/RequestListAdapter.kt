package com.example.w6dagger.request

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.databinding.ItemRequestBinding
import com.example.w6dagger.request.RequestListAdapter.RequestViewHolder

class RequestListAdapter : RecyclerView.Adapter<RequestViewHolder>() {

    lateinit var mRequests: List<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val currentRequest = mRequests[position]
        holder.bind(currentRequest)
    }

    fun setRequests(requests: List<String>) {
        mRequests = requests
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mRequests has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return mRequests.size
    }

    class RequestViewHolder private constructor(val binding: ItemRequestBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(itemBind: String) {
            binding.requestName = itemBind
        }

        companion object {
            fun from(parent: ViewGroup): RequestViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRequestBinding.inflate(layoutInflater, parent, false)
                return RequestViewHolder(binding)
            }
        }
    }

    fun getRequestAtPosition(position: Int): String {
        return mRequests[position]
    }
}