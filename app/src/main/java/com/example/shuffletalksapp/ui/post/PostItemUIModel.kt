package com.example.shuffletalksapp.ui.post

import android.graphics.drawable.Drawable
import com.example.shuffletalksapp.model.Like
import com.example.shuffletalksapp.model.Quote

data class PostItemUIModel (
    val username: String,
    val userId: String,
    val postcount: Int,
    val avatar: Int,
    val createdAt: String,
    val content: String,
    val commentId: String,
    val quotes: ArrayList<Quote>,
    val likes: ArrayList<Like>
    )