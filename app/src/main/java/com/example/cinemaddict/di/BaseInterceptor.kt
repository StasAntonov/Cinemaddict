package com.example.cinemaddict.di

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class BaseInterceptor : Interceptor, Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }

}