package com.example.graphql.presenter

import androidx.lifecycle.LiveData
import com.example.graphql.LaunchListQuery
import kotlinx.coroutines.flow.Flow

interface ListFragmentViewModel {
    val launchListFlow:Flow<List<LaunchListQuery.Launch>>
    val loadingListFlow:Flow<Boolean>

    fun getLaunchList()
}