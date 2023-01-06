package com.example.afreecaandroid.ui.view.cook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentCookDetailBinding

class CookDetailFragment : Fragment() {

    private var _binding: FragmentCookDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cook_detail, container, false)
        return binding.root
    }
}
