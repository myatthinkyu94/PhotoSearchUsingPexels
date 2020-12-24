package jp.co.pexels.photosearchusingpexels.ui.main.data.repository

import kotlinx.coroutines.flow.Flow

interface PhotosSearchedRepository {
    fun getPhotosSearched(query: String, perPage: Int, page: Int): Flow<Any?>
    suspend fun getPhotosSearchedFromAPI(query: String, perPage: Int, page: Int): Any?
}
