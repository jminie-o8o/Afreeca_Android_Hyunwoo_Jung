package com.example.afreecaandroid.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentTalkCamDetailBinding
import com.example.afreecaandroid.ui.viewmodel.TalkCamViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class TalkCamDetailFragment : Fragment() {

    private var _binding: FragmentTalkCamDetailBinding? = null
    private val binding get() = _binding!!
    private val talkCamViewModel: TalkCamViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_talk_cam_detail, container, false)
        binding.viewModel = talkCamViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
    }

    private fun hideBottomNavigation() {
        val bottomNavigation = (activity as MainActivity).findViewById<BottomNavigationView>(R.id.navigation_view)
        bottomNavigation.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
