package com.example.volley_h2.network

import com.android.volley.toolbox.StringRequest
import com.example.volley_h2.MyApp
import com.example.volley_h2.model.Data
import com.example.volley_h2.util.Constantas
import com.example.volley_h2.util.Constantas.BASE_URL

class VolleyInstance {
    fun get(responseHandler: ResponseHandler) {
        val getRequest = object : StringRequest(
            Method.GET,
            BASE_URL,
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.stackTraceToString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(getRequest)
    }

    fun getById(id: Int, responseHandler: ResponseHandler) {
        val getRequest = object : StringRequest(
            Method.GET,
            "$BASE_URL/$id",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.stackTraceToString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(getRequest)
    }

    fun post(body: Data, responseHandler: ResponseHandler) {
        val postRequest = object : StringRequest(
            Method.POST,
            BASE_URL,
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val hashMap = HashMap<String, String>()
                hashMap["avatar"] = body.avatar
                hashMap["email"] = body.email
                hashMap["first_name"] = body.first_name
                hashMap["id"] = body.id.toString()
                hashMap["last_name"] = body.last_name
                return hashMap
            }
        }
        MyApp.myApp?.addToRequestQueue(postRequest)
    }

    fun put(body: Data, responseHandler: ResponseHandler) {
        val putRequest = object : StringRequest(
            Method.PUT,
            "$BASE_URL/${body.id}",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.toString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(putRequest)
    }

    fun delete(id: Int, responseHandler: ResponseHandler) {
        val deleteRequest = object : StringRequest(
            Method.DELETE,
            "$BASE_URL/$id",
            {
                responseHandler.onResponse(it)
            },
            {
                responseHandler.onError(it?.toString())
            }
        ) {}
        MyApp.myApp?.addToRequestQueue(deleteRequest)
    }
}