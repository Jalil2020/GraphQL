package com.example.graphql.di

import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.domain.repository.iml.GraphQLRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GraphQLRepositoryModule {
    @[Binds Singleton]
    fun provideRepository(iml: GraphQLRepositoryImpl): GraphQLRepository
}