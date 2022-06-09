package com.example.shuffletalksapp.model

import android.graphics.drawable.Drawable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val userId: String,
    val username: String,
    val createdAt: String,
    var firstname: String,
    var lastname: String,
    val city: String,
    var email: String,
    val password: String,
    var postcount: Int,
    val avatar: Int
)
