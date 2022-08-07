package com.task.currency.app.di

import android.app.Application
import androidx.room.Room
import com.task.currency.app.data.local.HistoricalCurrencyDatabase
import com.task.currency.app.data.local.HistoricalCurrencyInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): HistoricalCurrencyDatabase {
        val applicationScope = CoroutineScope(SupervisorJob())
        return HistoricalCurrencyDatabase.getDatabase(application, applicationScope)
    }

    @Provides
    fun provideHistoricalCurrencyInfoDao(db: HistoricalCurrencyDatabase): HistoricalCurrencyInfoDao {
        return db.historicalCurrencyInfoDao()
    }
}