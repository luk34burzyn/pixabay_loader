package com.payback.pixabay.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
) : Parcelable
