package com.example.graphql.presenter

import androidx.lifecycle.LiveData
import com.example.graphql.*
import com.example.graphql.adapter.MutationBookMutation_ResponseAdapter
import kotlinx.coroutines.flow.Flow

interface DetailsFragmentViewModel {

    val getDetailsFlow: Flow<LaunchDetailsQuery.Launch>
    val setBookTripsFlow:Flow<MutationBookMutation.BookTrips>
    val setCancelTripFLow:Flow<CancelTripMutation.CancelTrip>
    val loadingFlow: Flow<Boolean>

    fun getDetails(id:String)
    fun setBookTrip(id: String)
    fun setCancelBookTrip(id: String)
}