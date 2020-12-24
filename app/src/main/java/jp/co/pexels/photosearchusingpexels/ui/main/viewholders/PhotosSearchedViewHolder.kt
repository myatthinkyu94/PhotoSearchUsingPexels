package jp.co.pexels.photosearchusingpexels.ui.main.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.co.pexels.photosearchusingpexels.R
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import jp.co.pexels.photosearchusingpexels.ui.main.adapter.PhotoSearchedAdapter

class PhotosSearchedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val ivPhoto: ImageView = itemView.findViewById(R.id.photoImageView)
    private val tvPhotographerName: TextView =
        itemView.findViewById(R.id.photographerNameTextView)

    fun bindData(photo: Photo?, photoClickListener: PhotoSearchedAdapter.PhotoClickListener) {
        photo?.let {
            Glide.with(ivPhoto.context)
                .load(photo.src.large2x)
                .centerInside()
                .into(ivPhoto)
            tvPhotographerName.text = photo.photographer
            itemView.setOnClickListener {
                photoClickListener.photoClick(adapterPosition, photo)
            }
        }
    }
}
