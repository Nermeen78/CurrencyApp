package com.task.currency.app.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.currency.app.data.model.CurrencyInfo
import com.task.currency.app.data.model.CurrencyRate
import kotlinx.coroutines.flow.Flow
import java.sql.Date

@Dao
interface HistoricalCurrencyInfoDao {

    @Query("SELECT * FROM currency_table")
    fun getHistoricalCurrencyInfo() : Flow<List<CurrencyInfo>>

    @Query("SELECT EXISTS(SELECT * FROM currency_table WHERE fromCurrency = :from and toCurrency=:to and date =:date)")
    fun isExists(from: String,to:String,date:String) :Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyInfo: CurrencyInfo) : Long

    @Delete
    suspend fun delete(currencyInfo: CurrencyInfo)

    @Query("DELETE FROM currency_table where date <= datetime('now', '-7 day') ")
    suspend fun deleteHistoricalCurrencyInfoBefore3Days()
}