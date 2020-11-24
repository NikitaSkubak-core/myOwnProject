package com.example.w6dagger.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
class User(@field:ColumnInfo(name = "userName") @field:PrimaryKey val userName: String)