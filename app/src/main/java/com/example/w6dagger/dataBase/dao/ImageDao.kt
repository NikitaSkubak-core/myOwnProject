package com.example.w6dagger.dataBase.dao

import androidx.room.*
import com.example.w6dagger.dataBase.Image
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class ImageDao {

    @Query("DELETE from Image where request = :request and userName = :user")
    abstract fun deleteRequest(user: String, request: String)

    @Query("DELETE from Image where userName = :user")
    abstract fun deleteImagesOfUser(user: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertImage(image: Image): Long

    @Query("SELECT request from Image where userName = :userName group by request")
    abstract fun getRequests(userName: String): Single<List<String>>

    @Query("select * from Image where userName = :userName and request = :request order by favorite DESC")
    abstract fun getImagesOfRequest(userName: String, request: String): Single<List<Image>>

    @Query("select * from Image where userName = :userName and favorite = 1")
    abstract fun getFavoriteImages(userName: String): Single<List<Image>>

    @Query("DELETE from Image")
    abstract fun deleteAllImages()

    @Update
    abstract fun updateImage(image: Image): Single<Int>

    @get:Query("select id_image from Image order by id_image DESC limit 1")
    abstract val maxId: Int

    @Delete
    abstract fun deleteImage(image: Image)

    @Query("delete from Image where userName = :user")
    abstract fun deleteRequests(user: String)
}