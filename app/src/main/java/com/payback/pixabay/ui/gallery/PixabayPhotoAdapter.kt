package com.payback.pixabay.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.payback.pixabay.R
import com.payback.pixabay.databinding.ItemPhotoBinding
import com.payback.pixabay.response.Hit

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Hit>() {
    override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean =
        oldItem == newItem
}

class PixabayPhotoAdapter (private val listener: OnItemClickListener) :
    PagingDataAdapter<Hit, PixabayPhotoAdapter.PhotoViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    item?.let {
                        listener.onItemClick(it)
                    }
                }
            }
        }

        fun bind(photo: Hit) {
            binding.apply {
                Glide.with(itemView)
                    .load(photo.webformatURL)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                textViewUserName.text = photo.user
                textViewTags.text = photo.tags
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(photo: Hit)
    }
}