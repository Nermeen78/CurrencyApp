package com.task.currency.app.data.api

import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun convert(
        toCurrency: String,
        fromCurrency: String,
        amount: String,
    ) = apiService.convert(toCurrency, fromCurrency, amount)
    suspend fun getCurrencySymbols()=apiService.getCurrencySymbols()
    suspend fun getLatestPopularCurrencies()=apiService.getLatestPopularCurrencies()
}