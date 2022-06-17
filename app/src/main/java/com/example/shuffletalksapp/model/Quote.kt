package com.example.shuffletalksapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quote(
    val commentId: String,
    val username: String,
    val userId: String,
    val text: String
): Parcelable
