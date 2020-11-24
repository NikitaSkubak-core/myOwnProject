package com.example.w6dagger.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.w6dagger.gallery.GalleryActivityViewModel
import com.example.w6dagger.camera.EditPictureActivityModel
import com.example.w6dagger.gallery.internalGallery.InternalGalleryActivityModel
import com.example.w6dagger.images.ImageViewModel
import com.example.w6dagger.main.UserViewModel
import com.example.w6dagger.main.ViewModelKey
import com.example.w6dagger.main.ViewModelProviderFactory
import com.example.w6dagger.request.RequestViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(value = UserViewModel::class)
    fun roomViewModel(roomViewModel: UserViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = RequestViewModel::class)
    fun requestViewModel(roomViewModel: RequestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = ImageViewModel::class)
    fun imageViewModel(roomViewModel: ImageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = EditPictureActivityModel::class)
    fun editPictureViewModel(roomViewModel: EditPictureActivityModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = InternalGalleryActivityModel::class)
    fun internalGalleryViewModel(roomViewModel: InternalGalleryActivityModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(value = GalleryActivityViewModel::class)
    fun galleryViewModel(roomViewModel: GalleryActivityViewModel): ViewModel
}