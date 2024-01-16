package com.mahmoudroid.task.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudroid.domain.entity.photos.PhotosItem
import com.mahmoudroid.task.R
import com.mahmoudroid.task.adapters.PhotosAdapter
import com.mahmoudroid.task.databinding.FragmentPhotosBinding
import com.mahmoudroid.task.utils.warningAlertDialog
import com.mahmoudroid.task.viewModel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "PhotosFragment"

@AndroidEntryPoint
class PhotosFragment : Fragment(R.layout.fragment_photos), PhotosAdapter.OnPhotosClickListener {
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var navController: NavController
    private lateinit var photosAdapter : PhotosAdapter
    private val viewModel: PhotosViewModel by viewModels()
    lateinit var userId: String
    private val photosList: MutableList<PhotosItem> by lazy {
        ArrayList<PhotosItem>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotosBinding.bind(view)
        navController = Navigation.findNavController(view)
        initUserData()
        initPhotosData()
        initSearch()
    }


    private fun initUserData() {
        userId = PhotosFragmentArgs.fromBundle(requireArguments()).id.toString()
    }

    private fun initPhotosData() {
        photosAdapter = PhotosAdapter(this)
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.getUser(userId).collect { it ->
                if (it.isSuccess){
                    photosAdapter.submitList(it.getOrNull())
                     it.getOrNull()?.map {
                        photosList.add(it)
                    }
                    binding.photosRecyclerView.adapter = photosAdapter
                    binding.photosRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
                    binding.progressBar.visibility = View.GONE
                } else{
                    Toast.makeText(requireContext(), "Failed to laod Data", Toast.LENGTH_SHORT)
                        .show()
                    requireContext().warningAlertDialog(
                        requireContext(),
                        "Check Your Internet Connection"
                    )
                }
            }
        }
    }

    override fun onItemClick(photos: PhotosItem) {
        val navDirection = PhotosFragmentDirections
            .actionPhotosFragmentToPhotoDetailFragment(photos.thumbnailUrl)
        navController.navigate(navDirection)
    }

    private fun initSearch() {
        binding.searchEditTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                search()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    private fun search() {
        if (!TextUtils.isEmpty(binding.searchEditTextText.text.toString())) {
            val photosFilteredList = photosList.filter {
                it.title.contains(binding.searchEditTextText.text.toString())
            }
            photosAdapter.submitList(photosFilteredList)
        } else {
            photosAdapter.submitList(photosList)
        }
    }
}