package com.example.graphql.domain.repository.iml

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.ApolloClient
import com.example.graphql.*
import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class GraphQLRepositoryImpl @Inject constructor(private val apolloClient: ApolloClient) :
    GraphQLRepository {

    override suspend fun getAllLaunch() =
        flow {
            val response = apolloClient.query(LaunchListQuery()).execute()
            if (!response.hasErrors()) emit(Result.Success(response.data?.launches?.launches))
            else emit(Result.Loading(true))
        }.catch {
            emit(Result.Error("Error response"))
        }.flowOn(Dispatchers.IO)

    override suspend fun getDetails(id: String): Flow<Result<LaunchDetailsQuery.Launch>> =
        flow {
            val response = apolloClient.query(LaunchDetailsQuery(id)).execute()

            if (!response.hasErrors() && response.data != null) emit(Result.Success(response.data!!.launch!!))
            else emit(Result.Error("Error response"))

        }.catch {
            emit(Result.Error("Error message response"))
        }.flowOn(Dispatchers.IO)

    override suspend fun setBookTrip(id: String): Flow<Result<MutationBookMutation.BookTrips>> =
        flow {
            val response = apolloClient.mutation(MutationBookMutation(arrayListOf(id))).execute()

            if (!response.hasErrors() && response.data != null) emit(Result.Success(response.data!!.bookTrips))
            else emit(Result.Loading(true))

        }.catch {
            emit(Result.Error("Error"))
        }.flowOn(Dispatchers.IO)

    override suspend fun setCancelBookTrip(id: String): Flow<Result<CancelTripMutation.CancelTrip>> =
        flow {
            emit(Result.Loading(true))
            val response = apolloClient.mutation(CancelTripMutation(id)).execute()

            if (!response.hasErrors() && response.data != null) emit(Result.Success(response.data!!.cancelTrip))
        }.catch {
            emit(Result.Error("Error"))
        }.flowOn(Dispatchers.IO)

    override suspend fun getSubscription(): Flow<Result<TripsBookedSubscription.Data>> =
        flow {
            emit(Result.Loading(true))
            val response = apolloClient.subscription(TripsBookedSubscription()).execute()
            if (!response.hasErrors()) emit(Result.Success(response.data!!))
            else emit(Result.Loading(true))

        }.catch {
            emit(Result.Error("Error message"))
        }.flowOn(Dispatchers.IO)
}