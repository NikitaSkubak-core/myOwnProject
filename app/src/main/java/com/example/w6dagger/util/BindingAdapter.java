package com.example.w6dagger.util;


import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("image")
    public static void getImage(ImageView imageView, String content) {
        if (imageView != null) {
            Glide.with(imageView.getRootView().getContext())
                    .load(Uri.fromFile(new File(content)))
                    .apply(new RequestOptions().centerCrop())
                    .into(imageView);
        }
    }

    @androidx.databinding.BindingAdapter("imageOfFlickr")
    public static void getImageOfFlickr(ImageView imageView, String content) {
        if (imageView != null) {
            Glide.with(imageView.getRootView().getContext())
                    .load(content)
                    .into(imageView);
        }
    }
}
