package com.example.w6dagger.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Image")
class Image(
    @field:ColumnInfo(name = "id_image") @field:PrimaryKey(autoGenerate = true) var id: Int = 0,
    @field:ColumnInfo(name = "userName") var userName: String,
    @field:ColumnInfo(name = "request") var request: String,
    @field:ColumnInfo(name = "response") var response: String,
    @field:ColumnInfo(name = "favorite") var favorite: Boolean
)