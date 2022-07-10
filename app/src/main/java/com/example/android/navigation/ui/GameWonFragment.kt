/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.android.navigation.R
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )

        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(GameWonFragmentDirections.gameWonToGamePlay())
        }

        //
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        Toast.makeText(
            context,
            "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}",
            Toast.LENGTH_LONG
        ).show()

        return binding.root
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {

        startActivity(getShareIntent())

    }

    // Creating our Share Intent
    private fun getShareIntent(): Intent {

        /**
         * Depreciated: .fromBundle(arguments), instead used requireArguments()
         */
        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        // Action: Send
        val shareIntent = Intent(Intent.ACTION_SEND)

        // PutExtra:
        // I mastered #UdacityAndroidTrivia with %1$d/%2$d correct questions!
        shareIntent.setType("text/plain")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
            )

        return shareIntent

    }

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
                menuInflater.inflate(R.menu.winner_menu, menu)

                // check if the activity resolves
                if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
                    // hide the menu item if it doesn't resolve
                    menu.findItem(R.id.share)?.isVisible = false
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                // Reference: https://developer.android.com/guide/navigation/navigation-navigate#id
                // view.findNavController().navigate(R.id.aboutFragment)

                when (menuItem.itemId) {
                    R.id.share -> shareSuccess()
                }

                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}
