package com.example.graphql.presenter

import androidx.lifecycle.LiveData
import com.example.graphql.LaunchListQuery

interface ListFragmentViewModel {
    fun getLaunchList():LiveData<List<LaunchListQuery.Launch>>
}