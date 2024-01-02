package com.example.cinemaddict.network


import com.example.cinemaddict.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BaseInterceptor : Interceptor {

    private val baseHeaders = mapOf(
        AUTHORIZATION to "$BEARER ${BuildConfig.ACCESS_KEY}",
        ACCEPT to APPLICATION
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeaders(baseHeaders)
            .build()
        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val ACCEPT = "accept"
        const val APPLICATION = "application/json"
        const val BEARER = "Bearer"
    }

}

private fun Request.Builder.addHeaders(baseHeaders: Map<String, String>): Request.Builder {
    baseHeaders.forEach(this::addHeader)
    return this
}
