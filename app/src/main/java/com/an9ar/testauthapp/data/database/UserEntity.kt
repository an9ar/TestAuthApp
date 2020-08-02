package com.an9ar.testauthapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val email: String,
    val password: String
)