package com.task.currency.app.data.repository

import com.task.currency.app.data.api.ApiService
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun convert(toCurrency: String, fromCurrency: String, amount: String) =
        apiService.convert(toCurrency, fromCurrency, amount)

    suspend fun getCurrencySymbols() = apiService.getCurrencySymbols()
}