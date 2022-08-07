package com.task.currency.app.di

import com.task.currency.app.data.api.ApiHelper
import com.task.currency.app.data.api.ApiService
import com.task.currency.app.data.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object DataRepositoryModule {
    @Provides
    fun provideCurrencyRepository(apiHelper: ApiHelper): CurrencyRepository =
        CurrencyRepository(apiHelper)
}