package com.mahmoudroid.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mahmoudroid.domain.entity.photos.PhotosItem
import com.mahmoudroid.task.databinding.PhotosItemBinding

class PhotosAdapter(
    private val listener: OnPhotosClickListener,
) : ListAdapter<PhotosItem, PhotosAdapter.PhotosViewHolder>(PhotosDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemBinding =
            PhotosItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PhotosDiffCallback : DiffUtil.ItemCallback<PhotosItem>() {
        override fun areItemsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItem: PhotosItem, newItem: PhotosItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class PhotosViewHolder(private val itemBinding: PhotosItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.apply {
                itemBinding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val currentItem = getItem(position)
                        listener.onItemClick(currentItem)
                    }
                }
            }

            itemView.setOnClickListener {
                listener.onItemClick(getItem(adapterPosition))
            }
        }

        fun bind(photos: PhotosItem) {
            Glide.with(itemBinding.root.context)
                .load(photos.thumbnailUrl)
                .into(itemBinding.imageView)
        }
    }

    interface OnPhotosClickListener {
        fun onItemClick(photos: PhotosItem)
    }
}
