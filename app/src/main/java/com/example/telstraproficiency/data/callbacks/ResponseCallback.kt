package com.example.telstraproficiency.data.callbacks

import androidx.lifecycle.MutableLiveData
import com.example.telstraproficiency.data.model.CountryModel

/**Common Interface for handling callbacks*/
interface ResponseCallback
{
    suspend fun onSuccess(data: CountryModel?)
    suspend fun onError(error:String?)
}
