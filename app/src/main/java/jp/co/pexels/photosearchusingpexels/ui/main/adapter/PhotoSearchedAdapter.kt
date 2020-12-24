package jp.co.pexels.photosearchusingpexels.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.co.pexels.photosearchusingpexels.R
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import jp.co.pexels.photosearchusingpexels.ui.main.viewholders.PhotosSearchedViewHolder

class PhotoSearchedAdapter(private val photoClickListener: PhotoClickListener) :

    ListAdapter<Photo, PhotosSearchedViewHolder>(
        SearchPhotoDiff
    ) {

    private object SearchPhotoDiff : DiffUtil.ItemCallback<Photo>() {

        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }

    interface PhotoClickListener {
        fun photoClick(position: Int, item: Photo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosSearchedViewHolder {
        return PhotosSearchedViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_photo_item, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: PhotosSearchedViewHolder,
        position: Int
    ) {
        holder.bindData(getItem(position), photoClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onViewRecycled(holder: PhotosSearchedViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.ivPhoto.context)
            .clear(holder.ivPhoto)
    }

}