package com.mahmoudroid.task.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.mahmoudroid.task.R
import com.mahmoudroid.task.databinding.FragmentPhotoDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : Fragment(R.layout.fragment_photo_details) {
    private lateinit var binding: FragmentPhotoDetailsBinding
    private lateinit var navController: NavController
    private lateinit var thumbnailUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)
        initPhoto()
    }
    private fun initPhoto() {
        binding.progressBar.visibility = View.VISIBLE
        thumbnailUrl = PhotoDetailFragmentArgs.fromBundle(requireArguments()).thubString
        Glide.with(requireContext())
            .load(thumbnailUrl)
            .into(binding.imageContainer)
        binding.progressBar.visibility = View.GONE
    }
}