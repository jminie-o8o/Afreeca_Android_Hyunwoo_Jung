package com.example.afreecaandroid.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentTalkCamBinding
import com.example.afreecaandroid.databinding.FragmentTravelBinding

class TravelFragment : Fragment() {

    private var _binding: FragmentTravelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_travel, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
