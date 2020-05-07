package com.example.telstraproficiency.data.repository

import com.example.telstraproficiency.data.callbacks.ResponseCallback
import com.example.telstraproficiency.data.model.CountryModel
import com.example.telstraproficiency.data.model.network.ApiClient
import retrofit2.Response

/**
 * method for fetching data from server
 * **/
class RepositoryViewModel {
    suspend fun retrieveCountryInformation(objCallback: ResponseCallback) {

        val data: Response<CountryModel>? = ApiClient.build()?.getList()
        if (data?.isSuccessful!!) {
            objCallback.onSuccess(data.body())  // callback to viewmodel class
        } else {
            objCallback.onError(data.errorBody().toString())
        }
    }
}
