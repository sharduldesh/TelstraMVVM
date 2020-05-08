package com.example.telstraproficiency.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.telstraproficiency.data.callbacks.ResponseCallback
import com.example.telstraproficiency.data.model.CountryModel
import com.example.telstraproficiency.data.repository.RepositoryViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.NotNull


/**
 * ViewModel class for home activity
 * **/

class ViewModelHome(@NotNull application: Application) : AndroidViewModel(application),
    ResponseCallback {
    private var repositoryViewModel: RepositoryViewModel = RepositoryViewModel()
    var countryResponse: MutableLiveData<CountryModel> =
        MutableLiveData<CountryModel>()

    var countryErrorResponse: MutableLiveData<String> =
        MutableLiveData<String>()

    init {
        CoroutineScope(IO).launch {
            repositoryViewModel.retrieveCountryInformation(this@ViewModelHome)
        }
    }

    /**
     * Calling API
     */
    fun getCountryInformation() {
        CoroutineScope(IO).launch {
            repositoryViewModel.retrieveCountryInformation(this@ViewModelHome)
        }
    }

    /**Success response from repository**/
    override suspend fun onSuccess(data: CountryModel?) {
        withContext(Main) {
            countryResponse.postValue(data)
        }
    }

/**Error response from repository**/
    override suspend fun onError(error: String?) {
        withContext(Main) {
            countryErrorResponse.postValue(error)
        }
    }
}


