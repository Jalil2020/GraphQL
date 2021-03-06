package com.example.graphql.presenter

import com.example.graphql.TripsBookedSubscription
import kotlinx.coroutines.flow.Flow

interface MainActivityViewModel {
    val subscriptionFlow:Flow<Int?>
    fun getSubscription()
}