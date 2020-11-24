package com.example.w6dagger.ui.user

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.w6dagger.R
import com.example.w6dagger.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserBinding
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_user)
    }

    fun addUser() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(binding.addUser.text)) {
            binding.addUser.error = getString(R.string.user_error_putting)
        } else {
            binding.addUser.error = null
            val user = binding.addUser.text.toString()
            replyIntent.putExtra(EXTRA_REPLY, user)
            setResult(RESULT_OK, replyIntent)
            finish()
        }

    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.w7dagger.REPLY"
    }

    fun changeTheme() {
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        recreate()
    }
}