package com.example.graphql.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class User(context: Context) {
    private lateinit var ref: SharedPreferences

    init {
        ref = context.getSharedPreferences("myref", Context.MODE_PRIVATE)
    }

    fun setToken(token: String) {
        ref.edit().putString("token", token).apply()
    }

    fun getToken(): String {
        return ref.getString("token", "").toString()
    }

    @SuppressLint("CommitPrefEdits")
    fun setEmail(email: String) {
        ref.edit().putString("email", email)
    }

    fun getEmail(): String = ref
        .getString("email", "").toString()
}