package com.example.graphql

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.okHttpClient
import com.example.graphql.presenter.MainActivityViewModel
import com.example.graphql.presenter.impl.MainActivityViewModelImpl
import com.example.graphql.type.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

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
            val text = when (val trips = it.tripsBooked) {
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

