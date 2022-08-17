package com.payback.pixabay.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Image response from API server
 *
 * @param total	The total number of hits.
 * @param totalHits	The number of images accessible through the API. By default, the API is limited to return a maximum of 500 images per query.
 * @param hits The list of @Hits (photos parameters)
 */
@Parcelize
data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
) : Parcelable
