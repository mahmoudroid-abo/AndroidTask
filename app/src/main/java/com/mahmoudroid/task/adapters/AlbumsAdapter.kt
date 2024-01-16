package com.mahmoudroid.task.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudroid.domain.entity.album.AlbumItem
import com.mahmoudroid.task.databinding.AlbumItemBinding

class AlbumsAdapter(var listener: OnAlbumClickListener) :
    ListAdapter<AlbumItem, AlbumsAdapter.AlbumViewHolder>(AlbumsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemBinding =
            AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    class AlbumsDiffCallback : DiffUtil.ItemCallback<AlbumItem>() {
        override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class AlbumViewHolder(private val itemBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
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

        fun bind(album: AlbumItem) {
            itemBinding.albumTitle.text = album.title
        }

    }

    interface OnAlbumClickListener {
        fun onItemClick(album: AlbumItem)
    }
}