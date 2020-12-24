package jp.co.pexels.photosearchusingpexels.ui.main.data.api

import androidx.annotation.WorkerThread
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.SearchedPhotos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PexelPhotosApi {

    @WorkerThread
    @GET("v1/search")
    suspend fun getSearchedPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchedPhotos>
}
