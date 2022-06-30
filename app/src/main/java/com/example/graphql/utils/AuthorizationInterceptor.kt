package com.example.graphql.utils

import android.content.Context
import com.example.graphql.data.local.User
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val context: Context):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", User(context).getToken())
            .build()
        return chain.proceed(request)
    }
}