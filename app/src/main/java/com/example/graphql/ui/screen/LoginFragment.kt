package com.example.graphql.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.apollographql.apollo3.api.Optional
import com.example.graphql.*
import com.example.graphql.databinding.FragmentLoginBinding
import com.example.graphql.presenter.LoginFragmentViewModel
import com.example.graphql.presenter.impl.LoginFragmentViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginFragmentViewModel by viewModels<LoginFragmentViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initObserver()


        binding.btnSend.setOnClickListener {
            viewModel.getLogin(binding.edtEmail.text.toString())
        }
    }

    private fun initObserver() {

        viewModel.loginFlow.onEach {
            User(requireContext()).setToken(it.token.toString())
            binding.txtToken.text = it.token.toString()
            Log.d("TAG", "initObserver: ${it.token}")
        }.launchIn(lifecycleScope)

        viewModel.loadingFlow.onEach {
            binding.progressBarLogin.isVisible = it
        }.launchIn(lifecycleScope)

    }
}