package com.example.graphql.presenter.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.graphql.LaunchListQuery
import com.example.graphql.domain.repository.iml.GraphQLRepositoryImpl
import com.example.graphql.presenter.ListFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListFragmentViewModelImpl @Inject constructor(private val repository: GraphQLRepositoryImpl) :
    ViewModel(), ListFragmentViewModel {
    override fun getLaunchList(): LiveData<List<LaunchListQuery.Launch>> {

        repository.getAllLaunch()
        return repository.launchLiveData
    }
}