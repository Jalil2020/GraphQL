package com.example.graphql.presenter.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.graphql.LoginMutation
import com.example.graphql.domain.repository.GraphQLRepository
import com.example.graphql.presenter.LoginFragmentViewModel
import com.example.graphql.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModelImpl @Inject constructor(private val repository: GraphQLRepository) :
    ViewModel(), LoginFragmentViewModel {
    override val loginFlow = MutableSharedFlow<LoginMutation.Login>()
    override val loadingFlow = MutableSharedFlow<Boolean>()

    override fun getLogin(email: String) {

        viewModelScope.launch(Dispatchers.IO) {
            loadingFlow.emit(true)
            repository.getLogin(email).collect {
                when (it) {
                    is Result.Success -> loginFlow.emit(it.data)
                    is Result.Loading -> loadingFlow.emit(true)
                    else -> Log.d("TAG", "getLogin: Error")
                }
                loadingFlow.emit(false)
            }
        }
    }

}