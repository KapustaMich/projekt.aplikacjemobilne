package com.example.myapplicationhp.repository

import com.example.myapplicationhp.repository.Character
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path

interface HPService {

    @GET("/api/characters")
    suspend fun getHPResponse(): Response<List<Character>>

    @GET("/api/character/{id}")
    suspend fun getHPDetailsResponse(@Path("id") id: String): Response<List<Character>>

    companion object {
        private const val API_URL = "https://hp-api.onrender.com"

        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val hpService: HPService by lazy {
            retrofit.create(HPService::class.java)
        }
    }
}