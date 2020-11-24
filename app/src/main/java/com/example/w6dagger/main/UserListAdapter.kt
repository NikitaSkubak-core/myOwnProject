package com.example.w6dagger.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.w6dagger.dataBase.User
import com.example.w6dagger.databinding.ItemUserBinding
import com.example.w6dagger.main.UserListAdapter.UserViewHolder
import javax.inject.Inject
import javax.inject.Singleton


class UserListAdapter : RecyclerView.Adapter<UserViewHolder>() {
    lateinit var usersList // Cached copy of words
            : List<User>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = usersList[position]
        holder.bind(currentUser.userName)
    }

    fun setUsers(users: List<User>) {
        usersList = users
        notifyDataSetChanged()
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    override fun getItemCount(): Int {
        return usersList.size
    }

    class UserViewHolder private constructor(var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun bind(bindItem: String) {
            binding.userName = bindItem
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
                return UserViewHolder(binding)
            }
        }
    }

    fun getUserAtPosition(position: Int): User {
        return usersList[position]
    }
}