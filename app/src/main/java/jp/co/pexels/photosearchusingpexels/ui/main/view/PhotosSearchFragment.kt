package jp.co.pexels.photosearchusingpexels.ui.main.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.co.pexels.photosearchusingpexels.R
import jp.co.pexels.photosearchusingpexels.ui.main.adapter.PhotoSearchedAdapter
import jp.co.pexels.photosearchusingpexels.ui.main.data.model.Photo
import jp.co.pexels.photosearchusingpexels.ui.main.viewmodel.PhotosSearchedViewModel
import kotlinx.android.synthetic.main.photos_searched_fragment.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosSearchFragment : Fragment(), PhotoSearchedAdapter.PhotoClickListener {

    private val searchedPhotosViewModel by viewModel<PhotosSearchedViewModel>()
    private lateinit var searchedPhotoAdapter: PhotoSearchedAdapter
    private lateinit var navController: NavController
    private var opened: Boolean = false
    private val observer = Observer<List<Photo>> { handleResponse(it) }
    private var nextPage = 1
    private val searchPage = 1
    private var perPage = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return view ?: inflater.inflate(R.layout.photos_searched_fragment, container, false)
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        searchView.requestFocus()
        initViews()

        val rvList = view.findViewById<RecyclerView>(R.id.recyclerView)
        val layout = LinearLayoutManager(activity)
        rvList.addOnScrollListener(scrollListener())
        val decorator = DividerItemDecoration(activity, layout.orientation)
        rvList.addItemDecoration(decorator)

        searchedPhotosViewModel.searchedPhotosLiveData.observe(viewLifecycleOwner, observer)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
                if (searchView.query.toString().length > 0) {
                    searchedPhotosViewModel.getSearchedPhotos(searchView.query.toString(), perPage, searchPage)
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                //Start filtering the list as user start entering the characters
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        searchView.requestFocus()
        if (!opened) {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                searchView,
                InputMethodManager.SHOW_IMPLICIT
            )
            opened = true
        }
        searchedPhotosViewModel.searchedPhotosLiveData.observe(viewLifecycleOwner, observer)
    }

    private fun initViews() {
        searchedPhotoAdapter = PhotoSearchedAdapter(this)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchedPhotoAdapter
            recycledViewPool.clear()
        }
    }

    private fun handleResponse(it: List<Photo>) {
        bindData(it)
    }

    private fun bindData(photos: List<Photo>) {
        searchedPhotoAdapter.submitList(photos)
        searchedPhotoAdapter.notifyDataSetChanged()
    }

    override fun photoClick(position: Int, item: Photo) {
        val action: PhotosSearchFragmentDirections.ActionPhotosSearchFragmentToPhotoDetailsFragment =
            PhotosSearchFragmentDirections.actionPhotosSearchFragmentToPhotoDetailsFragment(item)
        navController.navigate(action)
    }

    private inner class  scrollListener: RecyclerView.OnScrollListener() {
        @InternalCoroutinesApi
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_IDLE ->{
                    if (!recyclerView.canScrollVertically(1)) {
                        if (searchView.query.toString().isNotEmpty()) {
                            searchedPhotosViewModel.getSearchedPhotosReload(searchView.query.toString(), perPage, ++nextPage)
                        }
                    }
                }
                RecyclerView.SCROLL_STATE_DRAGGING ->
                    println("Scrolling now")
                RecyclerView.SCROLL_STATE_SETTLING ->
                    println("Scroll Settling")
            }
        }
    }
}


