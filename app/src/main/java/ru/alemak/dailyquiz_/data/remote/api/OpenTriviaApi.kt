package ru.alemak.dailyquiz_.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.alemak.dailyquiz_.data.remote.dto.TriviaResponse

interface OpenTriviaApi {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 5,
        @Query("type") type: String = "multiple"
    ): TriviaResponse
}