package com.payback.pixabay.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.payback.pixabay.R
import com.bumptech.glide.request.target.Target
import com.payback.pixabay.databinding.FragmentDetailsBinding
import com.payback.pixabay.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args by navArgs<DetailsFragmentArgs>()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
    }

    private fun initBinding(){

        val photo = args.photo
        Glide.with(this@DetailsFragment)
            .load(photo.largeImageURL)
            .error(R.drawable.ic_error)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.isVisible = false
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    Glide.with(this@DetailsFragment)
                        .load(photo.userImageURL)
                        .error(R.drawable.ic_user)
                        .into(binding.imageUser)
                    binding.progressBar.isVisible = false
                    binding.textTags.text = photo.tags
                    binding.textNameUser.text = photo.user
                    binding.numberOfLikes.text = photo.likes.toString()
                    binding.numberOfDownloads.text = photo.downloads.toString()
                    binding.numberOfComments.text = photo.comments.toString()
                    return false
                }
            })
            .into(binding.imageView)
    }
}