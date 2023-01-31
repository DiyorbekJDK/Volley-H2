package com.example.volley_h2.network

interface ResponseHandler {

    fun onResponse(string: String)

    fun onError(string: String?)
}