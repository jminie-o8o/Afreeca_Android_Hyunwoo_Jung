package com.example.afreecaandroid.ui.view.cook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.afreecaandroid.ui.adapter.UiDataPagingAdapter
import com.example.afreecaandroid.ui.view.MainActivity
import com.example.afreecaandroid.ui.view.talkcam.TalkCamFragmentDirections
import com.example.afreecaandroid.ui.viewmodel.CookViewModel
import com.example.afreecaandroid.ui.viewmodel.TalkCamViewModel
import com.example.afreecaandroid.uitl.UiState
import com.example.afreecaandroid.uitl.collectLatestStateFlow
import com.google.android.material.bottomnavigation.BottomNavigationView

class CookFragment : Fragment() {

    private var _binding: FragmentCookBinding? = null
    private val binding get() = _binding!!
    private val cookViewModel: CookViewModel by activityViewModels()
    private lateinit var uiDataPagingAdapter: UiDataPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cook, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiDataPagingAdapter = UiDataPagingAdapter()
        setupRecyclerView(uiDataPagingAdapter)
        setTalkCamDataByUiState()
        getTalkCamData()
        showListEmptyText()
        setClickListenerFromAdapter(uiDataPagingAdapter)
        showBottomNavigation()
    }

    private fun setupRecyclerView(uiDataPagingAdapter: UiDataPagingAdapter) {
        binding.rvCook.apply {
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
                    uiDataPagingAdapter.submitData(uiState.data)
                }
            }
        }
    }

    private fun showListEmptyText() {
        uiDataPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = uiDataPagingAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylistCook.isVisible = isListEmpty
        }
    }

    private fun setClickListenerFromAdapter(uiDataPagingAdapter: UiDataPagingAdapter) {
        uiDataPagingAdapter.setOnItemClickListener { uiData ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
