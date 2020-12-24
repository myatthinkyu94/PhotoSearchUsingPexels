package jp.co.pexels.photosearchusingpexels.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import jp.co.pexels.photosearchusingpexels.ui.main.data.repository.PhotosSearchedRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhotosSearchedViewModel(private val photosSearchedRepositoryImpl: PhotosSearchedRepositoryImpl) :
    ViewModel() {

    var mutableSearchedPhotosLiveData = MutableLiveData<List<Photo>>()
    private var getPhotosSearchedJob: Job? = null
    val searchedPhotosLiveData: LiveData<List<Photo>> = mutableSearchedPhotosLiveData
    private val mIssueSearchedPhotosLiveData = MutableLiveData<MutableList<Photo>>()
    init {
        mIssueSearchedPhotosLiveData.value = ArrayList<Photo>()
    }

    @InternalCoroutinesApi
    fun getSearchedPhotos(query: String, perPage: Int, page: Int) {
        getPhotosSearchedJob = viewModelScope.launch {
            photosSearchedRepositoryImpl.getPhotosSearched(query, perPage, page).collect {
                mIssueSearchedPhotosLiveData.value = ArrayList<Photo>()
                mutableSearchedPhotosLiveData.value = it as List<Photo>

                for (photo in it){
                    mIssueSearchedPhotosLiveData.value?.add(photo)
                }
            }
        }
    }

    fun getSearchedPhotosReload(query: String, perPage: Int, page: Int) {
        getPhotosSearchedJob = viewModelScope.launch {
            photosSearchedRepositoryImpl.getPhotosSearched(query, perPage, page).collect {
                for (photo in it as List<Photo>) {
                    mIssueSearchedPhotosLiveData.value?.add(photo)
                }
                mutableSearchedPhotosLiveData.value = mIssueSearchedPhotosLiveData.value
            }
        }
    }
}
