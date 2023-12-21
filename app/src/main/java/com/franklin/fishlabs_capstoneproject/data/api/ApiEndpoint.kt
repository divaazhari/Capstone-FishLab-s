package com.franklin.fishlabs_capstoneproject.data.api

import com.franklin.fishlabs_capstoneproject.model.MainModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiEndpoint {
    @GET("image")
    fun getImage(): Call<List<MainModel>>

}