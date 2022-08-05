package com.task.currency.app.di

import com.task.currency.app.data.api.ApiService
import com.task.currency.app.data.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object DataRepositoryModule {
    @Provides
    fun provideDataRepository(apiService: ApiService): CurrencyRepository =
        CurrencyRepository(apiService)
}