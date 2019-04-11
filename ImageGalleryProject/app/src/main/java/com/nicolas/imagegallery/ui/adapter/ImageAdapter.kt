package com.nicolas.imagegallery.ui.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nicolas.imagegallery.domain.Picture
import com.nicolas.imagegallery.extensions.loadImage


class ImageAdapter(val onItemClick: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: MutableList<Picture> = emptyList<Picture>().toMutableList()

    fun addAll(newItemList: MutableList<Picture>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val imageView = ImageView(parent.context)
        return ItemViewHolder(imageView)
    }

    override fun getItemCount() = itemList.count()

    fun getItemAt(position: Int): Picture? {
        return if (!itemList.isEmpty()) {
            itemList[position]
        } else {
            null
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                holder.load(getItemAt(position))
            }
        }
    }

    /**
     * Inner class
     */
    private inner class ItemViewHolder(itemView: ImageView) : RecyclerView.ViewHolder(itemView) {

        fun load(picture: Picture?) {
            picture?.let {
                (itemView as ImageView).loadImage(picture.croppedUrl) // it means the class using it...
                itemView.setOnClickListener { onItemClick(adapterPosition) }
            }
        }
    }


}