package com.example.graphql.domain.repository.iml

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.ApolloClient
import com.example.graphql.LaunchListQuery
import com.example.graphql.apolloClient
import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.utils.Result
import dagger.Module
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


class GraphQLRepositoryImpl @Inject constructor(apolloClient: ApolloClient) : GraphQLRepository {

    override suspend fun getAllLaunch() = flow<Result<Result<List<LaunchListQuery.Launch>>>> {
        val response = apolloClient.query(LaunchListQuery()).execute()

        if (response.hasErrors()) 
    }

}