package com.task.currency.app.data.model

data class LatestRatesResponse(
    var base: String,
    var date: String,
    var historical: Boolean,
    var rates: Map<String, Double>,
    var success: Boolean,
    var timestamp: Int,

) {
    fun getCurrenciesRates():ArrayList<CurrencyRate> {
        val currenciesRates = arrayListOf<CurrencyRate>()
        rates.map {
            currenciesRates.add(CurrencyRate(it.key, it.value.toString()))
        }
        return currenciesRates
    }
}