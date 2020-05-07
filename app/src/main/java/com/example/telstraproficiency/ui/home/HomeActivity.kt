package com.example.telstraproficiency.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.telstraproficiency.R
import com.example.telstraproficiency.ui.home.adapter.ListDataAdapter
import com.example.telstraproficiency.ui.home.viewmodel.ViewModelHome
import com.example.telstraproficiency.utils.Constant.Companion.somethingWentWrong
import com.example.telstraproficiency.utils.NetworkConnection
import com.example.telstraproficiency.utils.toast
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var mViewModelHome: ViewModelHome
    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private lateinit var mAdapter: ListDataAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mViewModelHome = ViewModelProvider(this).get(ViewModelHome::class.java)
        setupDialog()
        showDialog()

        swipeToRefresh.setOnRefreshListener {
            getInfo()
        }


        mAdapter = ListDataAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        country_list.layoutManager = linearLayoutManager
        country_list.adapter = mAdapter

        mViewModelHome.countryResponse.observe(this, Observer {
            hideDialog()
            swipeToRefresh.isRefreshing = false
            if (!it.rows.isNullOrEmpty()) {
                mAdapter.setList(it.rows)
            } else {
                toast(somethingWentWrong)
            }
        })

        mViewModelHome.countryErrorResponse.observe(this, Observer {
            hideDialog()
            toast(it)
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

    }

    /** Showing dialog when api call */
    private fun showDialog() {

        dialog.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    /** Hiding dialog */
    private fun hideDialog() {
        dialog.let {
            if (it.isShowing) {
                it.hide()
                it.dismiss()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        dialog.let {
            if (it.isShowing) {
                it.hide()
                it.dismiss()
            }
        }
    }

    /** get data from the viewModel */

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getInfo() {
        if (NetworkConnection.isNetworkConnected()) {
            showDialog()
            mViewModelHome.getCountryInformation()
        } else {
            if (swipeToRefresh.isRefreshing) {
                swipeToRefresh.isRefreshing = false
            }
            toast(getString(R.string.device_not_connected_to_internet))
        }
    }
}
