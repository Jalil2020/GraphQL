package com.example.graphql.presenter.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.*
import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.presenter.DetailsFragmentViewModel
import com.example.graphql.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsFragmentViewModelImpl @Inject constructor(private val repository: GraphQLRepository) :
    ViewModel(), DetailsFragmentViewModel {

    override val getDetailsFlow = MutableSharedFlow<LaunchDetailsQuery.Launch>()
    override val setBookTripsFlow = MutableSharedFlow<MutationBookMutation.BookTrips>()
    override val setCancelTripFLow = MutableSharedFlow<CancelTripMutation.CancelTrip>()
    override val loadingFlow = MutableSharedFlow<Boolean>()


    override fun getDetails(id: String) {
        viewModelScope.launch {
            repository.getDetails(id).collect {
                when (it) {
                    is Result.Success -> getDetailsFlow.emit(it.data)
                    is Result.Loading -> loadingFlow.emit(true)
                    else -> {
                        Log.d("TAG", "getDetails: Error")
                    }
                }
                loadingFlow.emit(false)
            }
        }
    }

    override fun setBookTrip(id: String) {

        viewModelScope.launch {
            loadingFlow.emit(true)
            repository.setBookTrip(id).collect {
                when (it) {
                    is Result.Success -> setBookTripsFlow.emit(it.data)
                    is Result.Loading -> loadingFlow.emit(true)
                    else -> {
                        Log.d("TAG", "getDetails: Error")
                    }
                }
                loadingFlow.emit(false)
            }
        }
    }

    override fun setCancelBookTrip(id: String) {
        viewModelScope.launch {
            loadingFlow.emit(true)
            repository.setCancelBookTrip(id).collect {
                when (it) {
                    is Result.Success -> setCancelTripFLow.emit(it.data)
                    is Result.Loading -> loadingFlow.emit(true)
                    else -> {
                        Log.d("TAG", "getDetails: Error")
                    }
                }
                loadingFlow.emit(false)
            }
        }
    }
}