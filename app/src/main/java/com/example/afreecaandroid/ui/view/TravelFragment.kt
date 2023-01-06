package com.example.afreecaandroid.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentTravelBinding
import com.example.afreecaandroid.ui.adapter.UiDataPagingAdapter
import com.example.afreecaandroid.ui.viewmodel.TalkCamViewModel
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
    private lateinit var uiDataPagingAdapter: UiDataPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiDataPagingAdapter = UiDataPagingAdapter()
        setupRecyclerView(uiDataPagingAdapter)
        setTalkCamDataByUiState()
        getTalkCamData()
        showListEmptyText()
        showBottomNavigation()
    }

    private fun setupRecyclerView(uiDataPagingAdapter: UiDataPagingAdapter) {
        binding.rvTravel.apply {
            adapter = uiDataPagingAdapter
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
                    uiDataPagingAdapter.submitData(uiState.data)
                }
            }
        }
    }

    private fun getTalkCamData() {
        travelViewModel.getTravelBroadCastList()
    }

    private fun showListEmptyText() {
        uiDataPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = uiDataPagingAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylistTravel.isVisible = isListEmpty
        }
    }

    private fun showBottomNavigation() {
        val bottomNavigation = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigation.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
