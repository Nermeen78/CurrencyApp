package com.task.currency.app.ui.viewmodel

import androidx.lifecycle.*
import com.task.currency.app.data.model.CurrencyInfo
import com.task.currency.app.data.repository.LocalCurrencyRepository
import com.task.currency.app.data.repository.RemoteCurrencyRepository
import com.task.currency.app.util.ApiResult
import com.task.currency.app.util.AppConst
import com.task.currency.app.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RemoteCurrencyRepository,
    private val localRepository: LocalCurrencyRepository
) : ViewModel() {
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

                withContext(Dispatchers.IO) {
                    insertHistoricalInfo(
                        CurrencyInfo(
                            0,
                            AppConst.TODAY_DATE,
                            fromCurrency,
                            toCurrency,
                            ("%.6f".format(
                                response.data?.result?.div(
                                    Integer.parseInt(amount)
                                )
                            ))
                        )
                    )
                }

                emit(response)

            } catch (exception: Exception) {

                emit(ApiResult.error(exception.stackTraceToString(), data = null))
            }
        }

    private suspend fun insertHistoricalInfo(currencyInfo: CurrencyInfo) {
        if (!localRepository.isExist(currencyInfo)) {
            localRepository.insert(currencyInfo)
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