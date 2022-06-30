package com.example.graphql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.graphql.presenter.MainActivityViewModel
import com.example.graphql.presenter.impl.MainActivityViewModelImpl
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels<MainActivityViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        viewModel.getSubscription()

        initObserver()
    }

    private fun initObserver() {

        viewModel.subscriptionFlow.onEach {
            Log.d("TAG", "initObserver: ${it}")
            val text = when (val trips = it) {
                null -> getString(R.string.error)
                -1 -> getString(R.string.tripCancelled)
                else -> getString(R.string.tripBooked, trips)
            }
            Snackbar.make(
                findViewById(R.id.main_layout),
                text,
                Snackbar.LENGTH_LONG
            ).show()
        }.launchIn(lifecycleScope)
    }
}

