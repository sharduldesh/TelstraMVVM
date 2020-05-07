package com.example.telstraproficiency.data.model.network

import com.example.telstraproficiency.data.model.CountryModel
import com.example.telstraproficiency.utils.Constant
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface
{
    /**
     * get JSON from server
     */
    @GET(Constant.countryUrl)
    suspend fun getList(): Response<CountryModel>
}