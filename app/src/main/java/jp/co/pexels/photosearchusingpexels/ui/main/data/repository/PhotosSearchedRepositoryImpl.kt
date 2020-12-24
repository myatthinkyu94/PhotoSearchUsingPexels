package jp.co.pexels.photosearchusingpexels.ui.main.data.repository

import android.content.Context
import android.widget.Toast
import jp.co.pexels.photosearchusingpexels.ui.main.data.api.PexelPhotosApi
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody

class PhotosSearchedRepositoryImpl(private val api: PexelPhotosApi) : PhotosSearchedRepository {

    override fun getPhotosSearched(query: String, perPage: Int, page: Int) = flow {
        emit((getPhotosSearchedFromAPI(query, perPage, page)))
    }

    override suspend fun getPhotosSearchedFromAPI(query: String, perPage: Int, page: Int) =
        api.getSearchedPhotos(query, perPage, page)
            .run {
                if (isSuccessful && body() != null) {
                    return@run body()?.photos
                } else {
                    fun Context.toast(message: CharSequence) =
                        Toast.makeText(this, ResponseBody.toString(), Toast.LENGTH_SHORT).show()
                    return@run ArrayList<Photo>()
                }
            }
}
