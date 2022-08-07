package com.task.currency.app.data.api

import com.task.currency.app.data.model.ConvertResponse
import com.task.currency.app.data.model.LatestRatesResponse
import com.task.currency.app.data.model.SymbolsResponse
import com.task.currency.app.util.AppConst
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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
    suspend fun getCurrencySymbols(): Response<SymbolsResponse>

    /**
     * This Function returning latest rate for 10 popular currencies.
     * @param date The today date.
     * @param symbols  a list of comma-separated currency codes to limit output currencies.
     * @param baseCurrency  the three-letter currency code of your base currency.
     * @return response contains the available currencies
     */
    @GET("{date}")
    suspend fun getLatestPopularCurrencies(
        @Path("date") date: String = AppConst.TODAY_DATE,
        @Query("symbols") symbols: String = AppConst.POPULAR_CURRENCIES,
        @Query("base") baseCurrency: String = AppConst.BASECURRENCY
    ): Response<LatestRatesResponse>
}