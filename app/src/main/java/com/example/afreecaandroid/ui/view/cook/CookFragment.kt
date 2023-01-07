package com.example.afreecaandroid.ui.view.cook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentCookBinding
import com.example.afreecaandroid.ui.adapter.CookPagingAdapter
import com.example.afreecaandroid.ui.adapter.TalkCamPagingAdapter
import com.example.afreecaandroid.ui.view.MainActivity
import com.example.afreecaandroid.ui.viewmodel.CookViewModel
import com.example.afreecaandroid.uitl.UiState
import com.example.afreecaandroid.uitl.collectLatestStateFlow
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CookFragment : Fragment() {

    private var _binding: FragmentCookBinding? = null
    private val binding get() = _binding!!
    private val cookViewModel: CookViewModel by activityViewModels()
    private lateinit var cookPagingAdapter: CookPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cook, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cookPagingAdapter = CookPagingAdapter()
        setupRecyclerView(cookPagingAdapter)
        setTalkCamDataByUiState()
        getTalkCamData()
        showListEmptyText()
        setClickListenerFromAdapter(cookPagingAdapter)
        showBottomNavigation()
        handlePagingSourceError(cookPagingAdapter)
        observeError()
    }

    private fun setupRecyclerView(cookPagingAdapter: CookPagingAdapter) {
        binding.rvCook.apply {
            adapter = cookPagingAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun setTalkCamDataByUiState() {
        collectLatestStateFlow(cookViewModel.cookBroadCastList) { uiState ->
            when(uiState) {
                is UiState.Loading -> {
                    with(binding) {
                        progressBarCook.isVisible = true
                        tvEmptylistCook.isVisible = false
                        rvCook.isVisible = false
                    }
                }
                is UiState.Error -> {
                    with(binding) {
                        progressBarCook.isVisible = false
                        tvEmptylistCook.isVisible = true
                        rvCook.isVisible = false
                    }
                }
                is UiState.Success -> {
                    with(binding) {
                        progressBarCook.isVisible = false
                        tvEmptylistCook.isVisible = false
                        rvCook.isVisible = true
                    }
                    cookPagingAdapter.submitData(uiState.data)
                }
            }
        }
    }

    private fun showListEmptyText() {
        cookPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = cookPagingAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylistCook.isVisible = isListEmpty
        }
    }

    private fun setClickListenerFromAdapter(cookPagingAdapter: CookPagingAdapter) {
        cookPagingAdapter.setOnItemClickListener { uiData ->
            cookViewModel.setCookDetailData(uiData)
            val actions = CookFragmentDirections.actionFragmentCookToCookDetailFragment()
            findNavController().navigate(actions)
        }
    }

    private fun showBottomNavigation() {
        val bottomNavigation = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun getTalkCamData() {
        cookViewModel.getCookBroadCastList()
    }

    private fun handlePagingSourceError(cookPagingAdapter: CookPagingAdapter) {
        cookPagingAdapter.addLoadStateListener { loadState ->
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.error?.let { throwable ->
                cookViewModel.handlePagingSourceError(throwable)
            }
        }
    }

    private fun observeError() {
        collectLatestStateFlow(cookViewModel.error) { CEHModel ->
            if (CEHModel.throwable != null) Toast.makeText(
                requireContext(),
                CEHModel.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            with(binding) {
                progressBarCook.isVisible = false
                tvEmptylistCook.isVisible = true
                rvCook.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
