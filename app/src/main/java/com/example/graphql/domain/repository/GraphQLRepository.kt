package com.example.graphql.domain.repository

import androidx.lifecycle.LiveData
import com.example.graphql.LaunchListQuery
import com.example.graphql.utils.Result
import kotlinx.coroutines.flow.Flow

interface GraphQLRepository {
    suspend fun getAllLaunch(): Flow<Result<List<LaunchListQuery.Launch>>>
}