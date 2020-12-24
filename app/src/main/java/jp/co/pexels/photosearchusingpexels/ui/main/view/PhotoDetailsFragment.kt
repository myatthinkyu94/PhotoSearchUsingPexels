package jp.co.pexels.photosearchusingpexels.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import jp.co.pexels.photosearchusingpexels.R
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import kotlinx.android.synthetic.main.photo_details_fragment.*

class PhotoDetailsFragment : Fragment() {
    private lateinit var photo: Photo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return view ?: inflater.inflate(R.layout.photo_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            val args: PhotoDetailsFragmentArgs =
                PhotoDetailsFragmentArgs.fromBundle(requireArguments())
            photo = args.photo
        }
        val currentZoom = photoImageView.maxZoom
        photoImageView.maxZoom = 5.0F
        Log.d("CurrentZoom!!!",currentZoom.toString())
        Glide.with(photoImageView.context)
            .load(photo.src.large2x)
            .centerInside()
            .into(photoImageView)
    }
}