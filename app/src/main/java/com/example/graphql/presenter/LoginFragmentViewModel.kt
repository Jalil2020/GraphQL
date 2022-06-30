package com.example.graphql.presenter

import com.example.graphql.LoginMutation
import kotlinx.coroutines.flow.Flow

interface LoginFragmentViewModel {

    val loginFlow:Flow<LoginMutation.Login>
    val loadingFlow:Flow<Boolean>
    fun getLogin(email:String)

}