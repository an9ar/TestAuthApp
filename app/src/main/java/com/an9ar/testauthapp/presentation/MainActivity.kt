package com.an9ar.testauthapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.an9ar.testauthapp.App
import com.an9ar.testauthapp.R
import com.an9ar.testauthapp.domain.models.UserModel
import com.an9ar.testauthapp.utils.afterTextChange
import com.an9ar.testauthapp.utils.custom_views.DrawableClickListener
import com.an9ar.testauthapp.utils.hideError
import com.an9ar.testauthapp.utils.showSnackbarResult
import com.an9ar.testauthapp.utils.tooltip.TooltipDelegate
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    var locationPermissionGranted: Boolean = false

    companion object{
        const val PERMISSIONS_REQUEST_CODE = 10
        private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissionsForLocation()
        }
        initUI()
    }

    fun inject(){
        App.appComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissionsForLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                PERMISSIONS_REQUIRED,
                PERMISSIONS_REQUEST_CODE
            )
        }
        else{
            locationPermissionGranted = true
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initUI(){
        et_login.afterTextChange { value ->
            viewModel.email.value = value
        }
        et_login.setOnFocusChangeListener { view, isFocused ->
            if (isFocused){
                til_password.hideError()
            }
        }
        et_password.afterTextChange { value ->
            viewModel.password.value = value
        }
        et_password.setOnFocusChangeListener { view, isFocused ->
            if (isFocused){
                til_login.hideError()
            }
        }
        et_password.setOnTouchListener(object : DrawableClickListener.RightDrawableClickListener(et_password) {
            override fun onDrawableClick(): Boolean {
                /*ViewTooltip
                    .on(this@MainActivity, et_password)
                    .autoHide(true, 3000)
                    .clickToHide(true)
                    .corner(30)
                    .arrowWidth(45)
                    .position(ViewTooltip.Position.TOP)
                    .customView(R.layout.custom_tooltip_view)
                    .text(getString(R.string.tooltip_hint))
                    .color(resources.getColor(R.color.colorPurple))
                    .show()*/
                TooltipDelegate(this@MainActivity)
                    .showTooltip(et_password, getString(R.string.tooltip_hint), true)
                return true
            }
        })
        viewModel.emailError.observe(this, Observer { error ->
            til_login.requestFocus()
            til_login.error = error
            til_password.hideError()
        })
        viewModel.passwordError.observe(this, Observer { error ->
            til_password.requestFocus()
            til_password.error = error
            til_login.hideError()
        })
        viewModel.snackbarResult.observe(this, Observer { result ->
            showSnackbarResult(root_view, result.resultText, resources.getColor(result.type))
        })
        viewModel.clearFields.observe(this, Observer { needToClear ->
            if (needToClear){
                et_login.text?.clear()
                et_password.text?.clear()
            }
        })
        viewModel.showLoader.observe(this, Observer { needToShow ->
            if (needToShow){
                loader.show()
            }
            else{
                loader.hide()
            }
        })
        btn_login.setOnClickListener {
            til_login.hideError()
            til_password.hideError()
            if (locationPermissionGranted){
                val coordinates = getCurrentCoordinates()
                if (coordinates != null){
                    viewModel.login(coordinates = coordinates)
                }
                else{
                    showSnackbarResult(root_view, getString(R.string.error_get_coordinates), resources.getColor(
                        R.color.snackbarErrorColor
                    ))
                }
            }
            else{
                showSnackbarResult(root_view, getString(R.string.error_geo_permission), resources.getColor(
                    R.color.snackbarErrorColor
                ))
            }

        }
        toolbar_create_user.setOnClickListener {
            til_login.hideError()
            til_password.hideError()
            viewModel.createUser(
                UserModel(
                    email = et_login.text.toString(),
                    password = et_password.text.toString()
                )
            )
        }
        toolbar_back.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentCoordinates(): Pair<Double, Double>?{
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        return if (location != null){
            Pair(first = location.longitude, second = location.latitude)
        } else{
            null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED != grantResults.firstOrNull()) {
                showSnackbarResult(root_view, getString(R.string.error_geo_permission), resources.getColor(
                    R.color.snackbarErrorColor
                ))
                locationPermissionGranted = false
            }
            else{
                locationPermissionGranted = true
            }
        }
    }
}