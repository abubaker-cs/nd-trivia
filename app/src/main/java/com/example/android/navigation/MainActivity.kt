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

package com.example.android.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.navigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        // Initialize the DrawerLayout
        drawerLayout = binding.drawerLayout

        // 1. To add support for the up button, we first need to make sure our Activity has an ActionBar
        val navController = this.findNavController(R.id.myNavHostFragment)

        // 2. Link the NavController to our ActionBar.
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Only show the drawerLayout in the TitleFragment
        navController.addOnDestinationChangedListener { _: NavController, nd: NavDestination, _: Bundle? ->
            if (nd.id == R.id.titleFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        // 3. We also need to setup the NavigationUI to know about the #navView defined in the activity_main.xml
        NavigationUI.setupWithNavController(binding.navView, navController)

    }

    // we need to have the Activity handle the navigateUp action from our Activity. To do this we override onSupportNavigateUp,
    override fun onSupportNavigateUp(): Boolean {

        // 3. Find the nav controller,
        val navController = this.findNavController(R.id.myNavHostFragment)

        // 4. we call navigateUp().
        return NavigationUI.navigateUp(navController, drawerLayout)

    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = findNavController(R.id.myNavHostFragment)
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }


}

// Tutorial: https://blog.codemagic.io/android-navigation-introduction/
