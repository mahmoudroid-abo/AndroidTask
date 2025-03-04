package com.mahmoudroid.task.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.facebook.shimmer.ShimmerFrameLayout
import com.mahmoudroid.domain.entity.album.AlbumItem
import com.mahmoudroid.task.R
import com.mahmoudroid.task.adapters.AlbumsAdapter
import com.mahmoudroid.task.databinding.FragmentUserBinding
import com.mahmoudroid.task.utils.random
import com.mahmoudroid.task.utils.warningAlertDialog
import com.mahmoudroid.task.viewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val TAG = "UserFragment"

@AndroidEntryPoint
class UserFragment : Fragment(R.layout.fragment_user),
    AlbumsAdapter.OnAlbumClickListener {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: FragmentUserBinding
    private lateinit var navController: NavController
    var randomId = -1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        initSharedPref()
        navController = Navigation.findNavController(view)
        initUserdata()
        initAlbumsAdapter()
    }

    private fun initUserdata() {
        Log.e(TAG, "initUserdata: ")
        Log.d("ShimmerDebug", "Starting shimmer effect")

        // Start shimmer and hide the actual CardView initially
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.userDataCardView.visibility = View.INVISIBLE

        lifecycleScope.launch {
            viewModel.getUser(randomId.toString()).collect { result ->
                if (result.isSuccess) {
                    Log.d("ShimmerDebug", "Data loaded successfully")
                    val user = result.getOrNull()

                    binding.userName.text = user?.name
                    binding.userAddress.text = user?.address?.let {
                        "${it.city}, ${it.suite}, ${it.street}"
                    }

                    // Stop shimmer and show the CardView
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.userDataCardView.visibility = View.VISIBLE

                    Log.d("ShimmerDebug", "Shimmer stopped, CardView set to VISIBLE")

                } else {
                    Log.e("ShimmerDebug", "Failed to load data")

                    Toast.makeText(requireContext(), "Failed To Load Data!", Toast.LENGTH_SHORT).show()

                    // Stop shimmer effect even if data fails to load, but still show the CardView
                    binding.shimmerLayout.stopShimmer()
                    binding.shimmerLayout.visibility = View.GONE
                    binding.userDataCardView.visibility = View.VISIBLE

                    Log.d("ShimmerDebug", "Shimmer stopped, CardView set to VISIBLE (failure case)")
                }
            }
        }
    }


    private fun initAlbumsAdapter() {
        val adapter = AlbumsAdapter(this)
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            viewModel.getAlbums(randomId.toString()).collect {
                if (it.isSuccess) {
                    adapter.submitList(it.getOrNull())
                    binding.albumsRecyclerView.adapter = adapter
                    binding.progressBar.visibility = View.GONE
                } else {
                    requireContext().warningAlertDialog(
                        requireContext(),
                        "Check Your Internet Connection"
                    )
                }
            }
        }
    }

    override fun onItemClick(album: AlbumItem) {
        val navDirection = UserFragmentDirections.actionUserFragmentToPhotosFragment(album.userId)
        navController.navigate(navDirection)
    }


    private fun initSharedPref() {
        val sharedPreferences =
            requireContext().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        randomId = sharedPreferences.getInt("randomId", -1)
        randomId = random()
        val editor = sharedPreferences.edit()
        editor.putInt(randomId.toString(), randomId)
        editor.apply()
    }


}