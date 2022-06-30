package com.example.graphql.presenter.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.LaunchListQuery
import com.example.graphql.domain.repository.iml.GraphQLRepositoryImpl
import com.example.graphql.presenter.ListFragmentViewModel
import com.example.graphql.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModelImpl @Inject constructor(private val repository: GraphQLRepositoryImpl) :
    ViewModel(), ListFragmentViewModel {

    override val launchListFlow = MutableSharedFlow<List<LaunchListQuery.Launch>>()
    override val loadingListFlow = MutableSharedFlow<Boolean>()

    override fun getLaunchList() {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllLaunch().collect {
                when (it) {
                    is Result.Success -> {
                        launchListFlow.emit(it.data as List<LaunchListQuery.Launch>)
                    }
                    is Result.Loading -> loadingListFlow.emit(true)
                    else -> {
                        Log.d("TAG", "getLaunchList: Error")
                    }
                }
                loadingListFlow.emit(false)
            }
        }
    }
}