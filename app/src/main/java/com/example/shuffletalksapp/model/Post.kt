package com.example.shuffletalksapp.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("_id")
    val postId: String,
    val comments: ArrayList<Comment>
) {

}