package com.payback.pixabay.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Hit - photo parameters
 *
 * @param id	A unique identifier for this image
 * @param q The String naming query
 * @param pageURL	Source page on Pixabay, which provides a download link for the original image of the dimension
 *                  @param imageWidth x @param imageHeight and the file size imageSize.
 * @param comments	Total number of comments
 * @param downloads	Total number of downloads
 * @param user_id	User ID of the contributor
 * @param previewURL	Low resolution images with a maximum width or height of 150 px (@param previewWidth x @param previewHeight).
 * @param user	User name of the contributor
 * @param tags	Tags related to the image
 * @param largeImageURL	Scaled image with a maximum width/height of 1280px.
 * @param views	Total number of views.
 * @param likes	Total number of likes
 * @param userImageURL	Profile picture URL (250 x 250 px).
 * @param webformatURL Medium sized image with a maximum width or height of 640 px (@param webformatWidth x  @param webformatHeight)
 */

@Parcelize
data class Hit(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
) : Parcelable
