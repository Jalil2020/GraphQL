package com.example.graphql.domain.repository

import androidx.lifecycle.LiveData
import com.example.graphql.*
import com.example.graphql.type.Subscription
import com.example.graphql.utils.Result
import kotlinx.coroutines.flow.Flow

interface GraphQLRepository {
    suspend fun getAllLaunch(): Flow<Result<List<LaunchListQuery.Launch?>?>>
    suspend fun getDetails(id:String):Flow<Result<LaunchDetailsQuery.Launch>>
    suspend fun setBookTrip(id: String):Flow<Result<MutationBookMutation.BookTrips>>
    suspend fun setCancelBookTrip(id: String): Flow<Result<CancelTripMutation.CancelTrip>>
    suspend fun getSubscription(): Flow<Result<TripsBookedSubscription.Data>>
}