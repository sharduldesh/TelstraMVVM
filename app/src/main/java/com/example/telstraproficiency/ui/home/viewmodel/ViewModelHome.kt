package com.example.telstraproficiency.ui.home.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.telstraproficiency.data.callbacks.ResponseCallback
import com.example.telstraproficiency.data.model.CountryModel
import com.example.telstraproficiency.data.repository.RepositoryViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import org.jetbrains.annotations.NotNull

class ViewModelHome(@NotNull application: Application) : AndroidViewModel(application),
    ResponseCallback {
    private var repositoryViewModel: RepositoryViewModel = RepositoryViewModel()
    var countryResponse: MutableLiveData<CountryModel> =
        MutableLiveData<CountryModel>()

    init {
        CoroutineScope(IO).launch {
            Log.d("sadasd", "")
            repositoryViewModel.retrieveCountryInformation(this@ViewModelHome)

        }

        /* Coroutines.main{
             val response = repositoryViewModel.retrieveCountryInformation()
             if (response != null) {
                 if(response.isSuccessful) {
                     countryResponse.postValue(response?.body())
                     viewModelListner?.onSuccess( countryResponse)
                 }
             }
         }*/
        //repositoryViewModel.retrieveCountryInformation(this)
    }

    /**
     * Calling API
     */
    fun getCountryInformation() {
        CoroutineScope(IO).launch {
            repositoryViewModel.retrieveCountryInformation(this@ViewModelHome)
        }
    }

    override suspend fun onSuccess(data: CountryModel?) {
        Log.d("onSuccess", "onSuccess")
        withContext(Main) {
            countryResponse.postValue(data)

        }
    }

    override fun onError(error: String?) {
        /* mBlogResponse.value?.error= error.toString()
         mBlogResponse.value?.isError=true*/
    }

    /* Log.d("sadasd","")
     Coroutines.main{
         Log.d("sadasd","")
         val response = repositoryViewModel.retrieveCountryInformation()
         if (response != null) {
             if(response.isSuccessful) {
                 countryResponse.postValue(response?.body())

             }
         }
     }*/

}


