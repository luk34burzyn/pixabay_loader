package com.payback.pixabay.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.payback.pixabay.R
import com.payback.pixabay.databinding.FragmentGalleryBinding
import com.payback.pixabay.response.Hit
import com.payback.pixabay.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery), PixabayPhotoAdapter.OnItemClickListener {
    private val viewModel: GalleryViewModel by viewModels()
    private val binding by viewBinding(FragmentGalleryBinding::bind)
    private val adapter = PixabayPhotoAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        initViewModels()
        initBindings()
        initLoaderState()
        createMenu(menuHost)
    }

    override fun onItemClick(photo: Hit) {
        findNavController().navigate(
            GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment(photo)
        )
    }

    private fun initBindings() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PixabayPhotoLoadStateAdapter { adapter.retry() },
            footer = PixabayPhotoLoadStateAdapter { adapter.retry() },
        )
        binding.buttonRetry.setOnClickListener {
            adapter.retry()
        }
    }

    private fun initViewModels() {
        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initLoaderState() {
        adapter.addLoadStateListener { loadStates ->
            binding.apply {
                progressBar.isVisible = loadStates.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadStates.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadStates.source.refresh is LoadState.Error
                textViewError.isVisible = loadStates.source.refresh is LoadState.Error

                if (loadStates.source.refresh is LoadState.NotLoading
                    && loadStates.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
    }

    private fun createMenu(menuHost: MenuHost) {
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_gallery, menu)
                val searcher = menu.findItem(R.id.action_search)
                val searchView = searcher.actionView as SearchView

                searchView.setOnQueryTextListener(
                    object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query != null) {
                                viewModel.searchPhotos(query)
                            }
                            return true
                        }

                        override fun onQueryTextChange(query: String?): Boolean  {
                            if (query != null) {
                                viewModel.searchPhotos(query)
                            }
                            return true
                        }
                    }
                )
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}