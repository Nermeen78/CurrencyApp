package com.task.currency.app.data.repository

import com.google.gson.Gson
import com.task.currency.app.data.api.ApiHelper
import com.task.currency.app.data.model.ApiErrorMessage
import com.task.currency.app.data.model.ConvertResponse
import com.task.currency.app.data.model.LatestRatesResponse
import com.task.currency.app.data.model.SymbolsResponse
import com.task.currency.app.util.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun convert(
        toCurrency: String,
        fromCurrency: String,
        amount: String
    ): ApiResult<ConvertResponse> =
        withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<ConvertResponse> =
                    apiHelper.convert(toCurrency, fromCurrency, amount)

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    ApiResult.success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ApiErrorMessage pojo
                    val errorResponse: ApiErrorMessage? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    ApiResult.error(errorResponse?.message ?: "Something went wrong", data = null)
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            }
        }


    suspend fun getCurrencySymbols() =
        withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<SymbolsResponse> = apiHelper.getCurrencySymbols()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    ApiResult.success(data = response.body()!!)
                } else {
                    // parsing api's own custom json error
                    // response in ApiErrorMessage pojo
                    val errorResponse: ApiErrorMessage? = convertErrorBody(response.errorBody())
                    // Simply returning api's own failure message
                    ApiResult.error(errorResponse?.message ?: "Something went wrong", data = null)
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                ApiResult.error(e.message ?: "Something went wrong", data = null)
            }
        }

    suspend fun getLatestPopularCurrencies() =
        withContext(Dispatchers.IO) {
            try {
                val response: Response<LatestRatesResponse> = apiHelper.getLatestPopularCurrencies()

                if (response.isSuccessful) {
                    ApiResult.success(data = response.body()!!)
                } else {

                    val errorResponse: ApiErrorMessage? = convertErrorBody(response.errorBody())

                    ApiResult.error(errorResponse?.message ?: "Something went wrong", data = null)
                }

            } catch (e: HttpException) {

                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: IOException) {

                ApiResult.error(e.message ?: "Something went wrong", data = null)
            } catch (e: Exception) {

                ApiResult.error(e.message ?: "Something went wrong", data = null)
            }
        }

    private fun convertErrorBody(errorBody: ResponseBody?): ApiErrorMessage? {
        return try {
            return Gson().fromJson(errorBody?.string(), ApiErrorMessage::class.java)
        } catch (exception: Exception) {
            null
        }
    }
}