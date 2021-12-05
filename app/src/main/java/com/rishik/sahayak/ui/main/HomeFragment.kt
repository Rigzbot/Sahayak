package com.rishik.sahayak.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rishik.sahayak.databinding.FragmentHomeBinding
import com.rishik.sahayak.util.SavedPreference

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        if(SavedPreference.getUserType(requireContext()) == "blind"){
            binding.helpCall.visibility = View.VISIBLE
        } else {
            binding.apply {
                cv1.visibility = View.VISIBLE
                cv2.visibility = View.VISIBLE
                cv3.visibility = View.VISIBLE
                button.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}