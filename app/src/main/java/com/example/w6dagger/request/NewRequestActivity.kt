package com.example.w6dagger.request

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.w6dagger.R
import com.example.w6dagger.api.FlickrApi
import com.example.w6dagger.databinding.ActivityNewRequestBinding
import javax.inject.Inject

class NewRequestActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewRequestBinding

    @Inject
    lateinit var flickr: FlickrApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_new_request)
    }

    companion object {
        const val EXTRA_REPLY_REQUEST = "com.example.android.w7dagger.REPLY"
    }

    fun addRequest() {
        val replyIntent = Intent()
        if (TextUtils.isEmpty(binding.requestEditText.text)) {
            binding.requestEditText.error = getString(R.string.request_error_putting)
        } else {
            val request = binding.requestEditText.text.toString()
            replyIntent.putExtra(EXTRA_REPLY_REQUEST, request)
            setResult(RESULT_OK, replyIntent)
            finish()
        }
    }
}