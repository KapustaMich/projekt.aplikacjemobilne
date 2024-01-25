package com.example.myapplicationhp.repository

import com.example.myapplicationhp.repository.Character
import retrofit2.Response

class HPRepository {
    suspend fun getHPResponse(): Response<List<Character>> =
        HPService.hpService.getHPResponse()

    suspend fun getHPDetailsResponse(id: String): Response<List<Character>> =
        HPService.hpService.getHPDetailsResponse(id)
}