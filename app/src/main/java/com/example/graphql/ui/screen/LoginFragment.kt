package com.example.graphql.ui.screen

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.network.okHttpClient
import com.example.graphql.*
import com.example.graphql.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(requireContext()))
            .build()

        val apolloClient = ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
//
//              apolloClient.mutation(LoginMutation(email)).execute()


        binding.btnSend.setOnClickListener {
            binding.progressBarLogin.visibility = View.VISIBLE
            lifecycleScope.launchWhenResumed {
                val email = "jalil@gmail.com"
                val response =
                    apolloClient.mutation(LoginMutation(email = Optional.presentIfNotNull(email)))
                        .execute()
                binding.txtToken.text = "Token: " + response.data?.login?.token
                val token = response.data?.login?.token

                if (token != null) {
                    User(requireContext()).setToken(token)
                }

//                Log.d("TAG", "onCreate: ${response.data?.login?.token}")
                binding.progressBarLogin.visibility = View.GONE
            }
        }
        lifecycleScope.launchWhenResumed {
            apolloClient.mutation(LoginMutation(Optional.presentIfNotNull(User(requireContext()).getToken())))
                .execute()
        }


    }

    private class AuthorizationInterceptor(val context: Context) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", User(context).getToken())
                .build()

            return chain.proceed(request)
        }
    }
}