package com.example.shuffletalksapp.ui.feed

import android.graphics.drawable.Drawable

data class FeedItemUIModel (
    val avatar: Int,
    val username: String,
    val postcount: Int,
    val createdAt: String,
    val content: String,
    val commentCount: Int,
    val postId: String
    )