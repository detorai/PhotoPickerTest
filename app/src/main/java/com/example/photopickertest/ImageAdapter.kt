package com.example.photopickertest

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageAdapter(private val context: Context, private val images: MutableList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ADD = 0
    private val VIEW_TYPE_IMAGE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ADD) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_add_photo, parent, false)
            AddPhotoViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image, parent, false)
            ImageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageViewHolder) {
            val imagePath = images[position]
            // Загружаем изображение с помощью Glide
            Glide.with(context).load(imagePath).into(holder.imageView)
        } else if (holder is AddPhotoViewHolder) {
            holder.itemView.setOnClickListener {
                ImagePicker.with(context as Activity)
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size + 1 // +1 для кнопки "Добавить"
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == images.size) VIEW_TYPE_ADD else VIEW_TYPE_IMAGE
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    inner class AddPhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addImage(imagePath: String) {
        images.add(imagePath)
        notifyItemInserted(images.size - 1)
    }
}