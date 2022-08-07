package com.task.currency.app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.task.currency.app.data.repository.CurrencyRepository
import com.task.currency.app.util.ApiResult
import com.task.currency.app.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CurrencyRepository) : ViewModel() {
    private val _amount = MutableLiveData("0")
    private val _convertedValue = MutableLiveData("0")
    val amount: LiveData<String> = _amount
    val convertedValue: LiveData<String> = _convertedValue

    fun convert(toCurrency: String, fromCurrency: String, amount: String, isReverse: Boolean) =
        liveData {
            emit(ApiResult.loading(null))
            try {
                val response = repository.convert(toCurrency, fromCurrency, amount)
                if (response.status == Status.SUCCESS) {
                    if (!isReverse)
                        _convertedValue.value = response.data?.result.toString()
                    else
                        _amount.value = response.data?.result.toString()
                }

                emit(response)

            } catch (exception: Exception) {

                emit(ApiResult.error(exception.stackTraceToString(), data = null))
            }
        }

    fun getCurrencySymbols() =
        liveData {
            emit(ApiResult.loading(null))
            try {

                emit(repository.getCurrencySymbols())
            } catch (exception: Exception) {
                emit(ApiResult.error(exception.message ?: "Error Occurred!", data = null))
            }
        }


}