package com.example.graphql.di

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.graphql.utils.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApolloModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(AuthorizationInterceptor(context))
            .build()

    @Provides
    @Singleton
    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient =
        ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .httpServerUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(okHttpClient).build()
}