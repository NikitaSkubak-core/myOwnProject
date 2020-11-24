package com.example.w6dagger.di.module

import com.example.w6dagger.camera.CameraActivity
import com.example.w6dagger.gallery.GalleryActivity
import com.example.w6dagger.gallery.internalGallery.InternalGalleryActivity
import com.example.w6dagger.images.FavoriteImagesActivity
import com.example.w6dagger.images.ImagesOfRequestActivity
import com.example.w6dagger.map.MapActivity
import com.example.w6dagger.request.NewRequestActivity
import com.example.w6dagger.request.ShowRequestsActivity
import com.example.w6dagger.ui.user.UserActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): UserActivity

    @ContributesAndroidInjector
    abstract fun contributeShowRequestsActivity(): ShowRequestsActivity

    @ContributesAndroidInjector
    abstract fun contributeImagesOfRequestActivity(): ImagesOfRequestActivity

    @ContributesAndroidInjector
    abstract fun contributeFavoriteImagesActivity(): FavoriteImagesActivity

    @ContributesAndroidInjector
    abstract fun contributeCameraActivity(): CameraActivity

    @ContributesAndroidInjector
    abstract fun contributeMapActivity(): MapActivity

    @ContributesAndroidInjector
    abstract fun contributeGalleryActivity(): GalleryActivity

    @ContributesAndroidInjector
    abstract fun contributeInternalGalleryActivity(): InternalGalleryActivity

    @ContributesAndroidInjector
    abstract fun contributeAddNewRequestActivity(): NewRequestActivity
}