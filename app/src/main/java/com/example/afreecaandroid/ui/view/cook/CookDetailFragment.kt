package com.example.afreecaandroid.ui.view.cook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.afreecaandroid.R
import com.example.afreecaandroid.databinding.FragmentCookDetailBinding
import com.example.afreecaandroid.ui.view.MainActivity
import com.example.afreecaandroid.ui.viewmodel.CookViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CookDetailFragment : Fragment() {

    private var _binding: FragmentCookDetailBinding? = null
    private val binding get() = _binding!!
    private val cookViewModel: CookViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cook_detail, container, false)
        binding.viewModel = cookViewModel
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
