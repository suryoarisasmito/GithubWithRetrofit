package com.example.githubapps

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    fun getUser() : Call<List<GithubUserItem>>

    @GET("users/{login}")
    fun getDetailUser(
        @Path("login") login : String
    ): Call<GithubUserDetail>
}