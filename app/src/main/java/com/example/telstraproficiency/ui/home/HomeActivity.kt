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
import com.example.telstraproficiency.utils.Constant.Companion.unknownError
import com.example.telstraproficiency.utils.NetworkConnection
import com.example.telstraproficiency.utils.toast
import kotlinx.android.synthetic.main.activity_home.*

/***
 * Activity class
 * */
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
        setupProgressDialog()
        showProgressDialog()

        swipeToRefresh.setOnRefreshListener {
            getCountryFeatureData()
        }


        mAdapter = ListDataAdapter(ArrayList(), this)
        linearLayoutManager = LinearLayoutManager(this)
        country_list.layoutManager = linearLayoutManager
        country_list.adapter = mAdapter

        mViewModelHome.countryResponse.observe(this, Observer {
            hideProgressDialog()
            swipeToRefresh.isRefreshing = false
            if (!it.rows.isNullOrEmpty()) {
                mAdapter.setList(it.rows)
            } else {
                toast(unknownError)
            }
        })

        mViewModelHome.countryErrorResponse.observe(this, Observer {
            hideProgressDialog()
            toast(it)
        })
    }

/**set up loading progress bar**/
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupProgressDialog() {
        builder = AlertDialog.Builder(this)
        builder.setCancelable(false) // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading_dialog)
        dialog = builder.create()

    }

    /** Showing dialog when api call */
    private fun showProgressDialog() {

        dialog.let {
            if (!it.isShowing) {
                it.show()
            }
        }
    }

    /** Hiding dialog */
    private fun hideProgressDialog() {
        dialog.let {
            if (it.isShowing) {
                it.hide()
                it.dismiss()
            }
        }

    }
/**onDetroy activity life cycle method**/
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
    private fun getCountryFeatureData() {
        if (NetworkConnection.isNetworkConnected()) {
            showProgressDialog()
            mViewModelHome.getCountryInformation()
        } else {
            if (swipeToRefresh.isRefreshing) {
                swipeToRefresh.isRefreshing = false
            }
            toast(getString(R.string.no_internet))
        }
    }
}
