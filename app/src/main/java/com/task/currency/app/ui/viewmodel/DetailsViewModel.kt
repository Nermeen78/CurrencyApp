package com.task.currency.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.task.currency.app.data.repository.CurrencyRepository
import com.task.currency.app.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {
    fun getLatestPopularCurrencies() =
        liveData {
            emit(ApiResult.loading(null))
            try {
                emit(repository.getLatestPopularCurrencies())
            } catch (exception: Exception) {
                emit(ApiResult.error(exception.message ?: "Error Occurred!", data = null))
            }
        }
}