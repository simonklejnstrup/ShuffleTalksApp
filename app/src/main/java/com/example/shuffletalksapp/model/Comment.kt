package com.example.shuffletalksapp.model

import com.google.gson.annotations.SerializedName

data class Comment(
    val userid: String,
    val username: String,
    @SerializedName("text")
    var content: String,
    @SerializedName("_id")
    val commentId: String,
    val likes: ArrayList<Like>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val quotes: ArrayList<Quote>

)
