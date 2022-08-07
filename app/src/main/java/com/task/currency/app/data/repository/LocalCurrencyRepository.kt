package com.task.currency.app.data.repository

import androidx.annotation.WorkerThread
import com.task.currency.app.data.local.HistoricalCurrencyInfoDao
import com.task.currency.app.data.model.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalCurrencyRepository @Inject constructor(private val historicalCurrencyInfoDao: HistoricalCurrencyInfoDao) {
    val historicalCurrencyInfo: Flow<List<CurrencyInfo>> =
        historicalCurrencyInfoDao.getHistoricalCurrencyInfo()

    fun isExist(currencyInfo: CurrencyInfo) = historicalCurrencyInfoDao.isExists(
        currencyInfo.from,
        currencyInfo.to,
        currencyInfo.date
    )

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(currencyInfo: CurrencyInfo) {
        if (!isExist(currencyInfo)
        )
            historicalCurrencyInfoDao.insert(currencyInfo)
    }
}