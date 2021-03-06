package com.example.graphql.presenter.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.presenter.MainActivityViewModel
import com.example.graphql.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModelImpl @Inject constructor(private val repository: GraphQLRepository) :
    ViewModel(), MainActivityViewModel {

    override val subscriptionFlow = MutableSharedFlow<Int?>()
    override fun getSubscription() {
        viewModelScope.launch {
            repository.getSubscription().collect{
                when(it){
                    is Result.Success -> subscriptionFlow.emit(it.data.tripsBooked)
                    else-> Log.d("TAG", "getSubscription: Error")
                }
            }
        }
    }
}