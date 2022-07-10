package com.example.android.navigation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // setContentView() does not exist in fragments, that's why we will inflate the layout using
        // DataBindingUtil.inflate()

        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

        // Method 01
        // Navigate: Title > Game
        // The complete onClickListener with Navigation
        // binding.playButton.setOnClickListener { view: View ->
        //     view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
        // }

        // Method 02
        //The complete onClickListener with Navigation using createNavigateOnClickListener
        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_titleFragment_to_gameFragment
            )
        )


        return binding.root
    }

}