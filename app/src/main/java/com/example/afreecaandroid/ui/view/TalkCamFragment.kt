package com.example.afreecaandroid.ui.view

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
import com.example.afreecaandroid.databinding.FragmentTalkCamBinding
import com.example.afreecaandroid.ui.adapter.TalkCamPagingAdapter
import com.example.afreecaandroid.ui.viewmodel.TalkCamViewModel
import com.example.afreecaandroid.uitl.UiState
import com.example.afreecaandroid.uitl.collectLatestStateFlow
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalkCamFragment : Fragment() {

    private var _binding: FragmentTalkCamBinding? = null
    private val binding get() = _binding!!
    private val talkCamViewModel: TalkCamViewModel by activityViewModels()
    private lateinit var talkCamPagingAdapter: TalkCamPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk_cam, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        talkCamPagingAdapter = TalkCamPagingAdapter()
        setupRecyclerView(talkCamPagingAdapter)
        setTalkCamDataByUiState()
        showListEmptyText()
        getTalkCamData()
        setClickListenerFromAdapter(talkCamPagingAdapter)
        showBottomNavigation()
    }

    private fun setupRecyclerView(talkCamPagingAdapter: TalkCamPagingAdapter) {
        binding.rvTalkCam.apply {
            adapter = talkCamPagingAdapter
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
        collectLatestStateFlow(talkCamViewModel.talkCamBroadCastList) { uiState ->
            when(uiState) {
                is UiState.Loading -> {
                    with(binding) {
                        progressBar.isVisible = true
                        tvEmptylist.isVisible = false
                        rvTalkCam.isVisible = false
                    }
                }
                is UiState.Error -> {
                    with(binding) {
                        progressBar.isVisible = false
                        tvEmptylist.isVisible = true
                        rvTalkCam.isVisible = false
                    }
                }
                is UiState.Success -> {
                    with(binding) {
                        progressBar.isVisible = false
                        tvEmptylist.isVisible = false
                        rvTalkCam.isVisible = true
                    }
                    talkCamPagingAdapter.submitData(uiState.data)
                }
            }
        }
    }

    private fun getTalkCamData() {
        talkCamViewModel.getTalkCamBroadCastList()
    }

    private fun showListEmptyText() {
        talkCamPagingAdapter.addLoadStateListener { combinedLoadStates ->
            val loadState = combinedLoadStates.source
            val isListEmpty = talkCamPagingAdapter.itemCount < 1
                    && loadState.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached

            binding.tvEmptylist.isVisible = isListEmpty
        }
    }

    private fun setClickListenerFromAdapter(talkCamPagingAdapter: TalkCamPagingAdapter) {
        talkCamPagingAdapter.setOnItemClickListener { talkCamData ->
            talkCamViewModel.setTalkCamDetailData(talkCamData)
            val actions = TalkCamFragmentDirections.actionFragmentTalkCamToFragmentTalkCamDetail()
            findNavController().navigate(actions)
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
