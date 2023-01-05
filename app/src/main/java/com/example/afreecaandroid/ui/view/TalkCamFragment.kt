package com.example.afreecaandroid.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentTalkCamBinding
import com.example.afreecaandroid.ui.adapter.TalkCamPagingAdapter
import com.example.afreecaandroid.ui.viewmodel.TalkCamViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TalkCamFragment : Fragment() {

    private var _binding: FragmentTalkCamBinding? = null
    private val binding get() = _binding!!
    private val talkCamViewModel: TalkCamViewModel by viewModels()
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
    }

    private fun setupRecyclerView(talkCamPagingAdapter: TalkCamPagingAdapter) {
        binding.rvTalkCam.apply {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
