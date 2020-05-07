package com.example.telstraproficiency.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.telstraproficiency.data.callbacks.ResponseCallback
import com.example.telstraproficiency.data.model.CountryModel
import com.example.telstraproficiency.data.model.network.ApiClient
import com.example.telstraproficiency.data.model.network.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * methid for fetchinf data from server
 * **/
class RepositoryViewModel {
    suspend fun retrieveCountryInformation(objCallback: ResponseCallback) {
        val listResponse: MutableLiveData<CountryModel> = MutableLiveData()

            val data: Response<CountryModel>? = ApiClient.build()?.getList()
            if (data?.isSuccessful!!) {
                listResponse.postValue(data.body())
                objCallback.onSuccess(data.body())  // callback to viewmodel class
                }
            else{
                objCallback.onError(data.errorBody().toString())
            }
    }
}
