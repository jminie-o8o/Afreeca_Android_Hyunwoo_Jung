package com.example.afreecaandroid.ui.view.travel

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
import com.example.afreecaandroid.databinding.FragmentTravelBinding
import com.example.afreecaandroid.ui.adapter.TalkCamPagingAdapter
import com.example.afreecaandroid.ui.adapter.TravelPagingAdapter
import com.example.afreecaandroid.ui.view.MainActivity
import com.example.afreecaandroid.ui.viewmodel.TravelViewModel
import com.example.afreecaandroid.uitl.UiState
import com.example.afreecaandroid.uitl.collectLatestStateFlow
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null
    private val binding get() = _binding!!
    private val travelViewModel: TravelViewModel by activityViewModels()
    private lateinit var travelPagingAdapter: TravelPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        travelPagingAdapter = TravelPagingAdapter()
        setupRecyclerView(travelPagingAdapter)
        setTalkCamDataByUiState()
        showListEmptyText()
        setClickListenerFromAdapter(travelPagingAdapter)
        showBottomNavigation()
        handlePagingSourceError(travelPagingAdapter)
        observeError()
    }

    private fun setupRecyclerView(travelPagingAdapter: TravelPagingAdapter) {
        binding.rvTravel.apply {
            adapter = travelPagingAdapter
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
        collectLatestStateFlow(travelViewModel.travelBroadCastList) { uiState ->
            when(uiState) {
                is UiState.Loading -> {
                    with(binding) {
                        progressBarTravel.isVisible = true
                        tvEmptylistTravel.isVisible = false
                        rvTravel.isVisible = false
                    }
                }
                is UiState.Error -> {
                    with(binding) {
                        progressBarTravel.isVisible = false
                        tvEmptylistTravel.isVisible = true
                        rvTravel.isVisible = false
                    }
                }
                is UiState.Success -> {
                    with(binding) {
                        progressBarTravel.isVisible = false
                        tvEmptylistTravel.isVisible = false
                        rvTravel.isVisible = true
                    }
                    travelPagingAdapter.submitData(uiState.data)
                }
            }
        }
    }

    private fun showListEmptyText() {
        travelPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = travelPagingAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylistTravel.isVisible = isListEmpty
        }
    }

    private fun setClickListenerFromAdapter(travelPagingAdapter: TravelPagingAdapter) {
        travelPagingAdapter.setOnItemClickListener { uiData ->
            travelViewModel.setTravelDetailData(uiData)
            val actions = TravelFragmentDirections.actionFragmentTravelToTravelFragmentDetail()
            findNavController().navigate(actions)
        }
    }

    private fun showBottomNavigation() {
        val bottomNavigation = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigation.visibility = View.VISIBLE
    }

    private fun handlePagingSourceError(travelPagingAdapter: TravelPagingAdapter) {
        travelPagingAdapter.addLoadStateListener { loadState ->
            val errorState = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.error?.let { throwable ->
                travelViewModel.handlePagingSourceError(throwable)
            }
        }
    }

    private fun observeError() {
        collectLatestStateFlow(travelViewModel.error) { CEHModel ->
            if (CEHModel.throwable != null) Toast.makeText(
                requireContext(),
                CEHModel.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            with(binding) {
                progressBarTravel.isVisible = false
                tvEmptylistTravel.isVisible = true
                rvTravel.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
