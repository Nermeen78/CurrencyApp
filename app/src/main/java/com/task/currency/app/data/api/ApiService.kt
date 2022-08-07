package com.task.currency.app.data.api

import androidx.lifecycle.LiveData
import com.task.currency.app.data.model.ConvertResponse
import com.task.currency.app.data.model.SymbolsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * This Function used to convert any amount from one currency to another.
     * @param toCurrency The three-letter currency code of the currency you would like to convert to.
     * @param fromCurrency The three-letter currency code of the currency you would like to convert from.
     * @param amount The amount to be converted.
     * @return response contains the result value
     */
    @GET("convert")
    suspend fun convert(
        @Query("to") toCurrency: String,
        @Query("from") fromCurrency: String,
        @Query("amount") amount: String,
    ): Response<ConvertResponse>


    /**
     * This Function returning all available currencies.
     * @return response contains the available currencies
     */
    @GET("symbols")
    suspend fun getCurrencySymbols():Response<SymbolsResponse>
}