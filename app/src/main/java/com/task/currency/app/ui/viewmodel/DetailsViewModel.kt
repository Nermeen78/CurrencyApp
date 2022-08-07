package com.task.currency.app.ui.viewmodel

import androidx.lifecycle.*
import com.task.currency.app.data.model.CurrencyInfo
import com.task.currency.app.data.repository.LocalCurrencyRepository
import com.task.currency.app.data.repository.RemoteCurrencyRepository
import com.task.currency.app.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val remoteRepository: RemoteCurrencyRepository,
    private val localRepository: LocalCurrencyRepository
) : ViewModel() {
    fun getLatestPopularCurrencies() =
        liveData {
            emit(ApiResult.loading(null))
            try {
                emit(remoteRepository.getLatestPopularCurrencies())
            } catch (exception: Exception) {
                emit(ApiResult.error(exception.message ?: "Error Occurred!", data = null))
            }
        }

    val historicalCurrencyInfo: LiveData<List<CurrencyInfo>> =
        localRepository.historicalCurrencyInfo.asLiveData()


}