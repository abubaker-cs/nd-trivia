package com.example.android.navigation.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val binding: FragmentTitleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false
        )

        binding.playButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(TitleFragmentDirections.onboardingToGamePlay())
        )

        return binding.root
    }

    /**
     * Depreciated in API Level 28+ :
     * 1. setHasOptionsMenu(true)
     * 2. onCreateOptionsMenu
     */

    // https://developer.android.com/jetpack/androidx/releases/activity#1.4.0-alpha01
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // The usage of an interface lets you inject your own implementation
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            // Handle the menu selection
            // Reference: https://developer.android.com/guide/navigation/navigation-navigate#id
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                when (menuItem.itemId) {
                    R.id.about ->
                        view.findNavController().navigate(R.id.about)

                }

                // val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
                // drawerLayout.closeDrawer(GravityCompat.START)

                return true

            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}