package com.an9ar.testauthapp.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.an9ar.testauthapp.*
import com.an9ar.testauthapp.data.database.UserEntity
import com.an9ar.testauthapp.domain.models.UserModel
import com.an9ar.testauthapp.domain.repository.UsersRepository
import com.an9ar.testauthapp.domain.repository.WeatherRepository
import com.an9ar.testauthapp.domain.usecase.LoginUseCase
import com.an9ar.testauthapp.domain.usecase.RegisterUseCase
import com.an9ar.testauthapp.presentation.enums.LoginFields
import com.an9ar.testauthapp.presentation.enums.SnackbarResultType
import com.an9ar.testauthapp.presentation.handlers.AuthErrorHandler
import com.an9ar.testauthapp.presentation.models.SnackbarResultModel
import com.an9ar.testauthapp.presentation.providers.ResourceProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.roundToInt

class MainViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val errorHandler: AuthErrorHandler,
    private val usersRepository: UsersRepository,
    private val weatherRepository: WeatherRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>()
    val clearFields = MutableLiveData<Boolean>()
    val showLoader = MutableLiveData<Boolean>()
    val disposables: CompositeDisposable by lazy { CompositeDisposable() }
    val snackbarResult = MutableLiveData<SnackbarResultModel>()

    fun login(coordinates: Pair<Double, Double>){
        loginUseCase.checkEmailValidation(email = email.value)
            .andThen(loginUseCase.checkPasswordValidation(password = password.value))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    showLoader()
                    usersRepository.getListOfUsers()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onError = {
                                hideLoader()
                            },
                            onSuccess = { listOfUsers ->
                                val result = listOfUsers.find {
                                    it.email == email.value && it.password == password.value
                                }
                                if (result == null){
                                    hideLoader()
                                    showSnackbar(SnackbarResultType.ERROR, resourceProvider.getString(R.string.login_result_error))
                                }
                                else{
                                    weatherRepository.getCurrentWeather(
                                        longitude = coordinates.first,
                                        latitude = coordinates.second,
                                        token = BuildConfig.SERVER_TOKEN
                                    ).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeBy(
                                            onError = { throwable ->
                                                val errorModel = errorHandler.handle(error = throwable)
                                                hideLoader()
                                                if (errorModel==null){
                                                    showSnackbar(SnackbarResultType.ERROR, resourceProvider.getString(R.string.weather_query_error))
                                                }
                                                else{
                                                    showSnackbar(SnackbarResultType.ERROR, errorModel.errorText)
                                                }
                                            },
                                            onSuccess = { weatherResult ->
                                                hideLoader()
                                                showSnackbar(SnackbarResultType.NEUTRAL, resourceProvider.getString(
                                                    R.string.weather_query_success,
                                                    weatherResult.name,
                                                    weatherResult.mainInformation.temp.roundToInt(),
                                                    weatherResult.weather.first().description.capitalize()
                                                ))
                                            }
                                        )
                                }
                            }
                        )
                },
                onError = { throwable ->
                    val errorModel = errorHandler.handle(error = throwable)
                    errorModel?.let {
                        if (it.field == LoginFields.EMAIL){
                            emailError.value = it.errorText
                        }
                        else{
                            passwordError.value = it.errorText
                        }
                    }
                }
            )
            .addTo(disposables)

    }

    fun createUser(user: UserModel){
        registerUseCase.checkEmailValidation(email = email.value)
            .andThen(registerUseCase.checkPasswordValidation(password = password.value))
            .andThen(registerUseCase.checkExistingUser(email = user.email, password = user.password))
            .andThen(usersRepository.insertUser(user = UserEntity(email = user.email, password = user.password)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { throwable ->
                    val errorModel = errorHandler.handle(error = throwable)
                    if (errorModel==null){
                        showSnackbar(SnackbarResultType.ERROR, resourceProvider.getString(R.string.registration_result_error))
                    }
                    else{
                        when(errorModel.field){
                            LoginFields.EMAIL -> emailError.value = errorModel.errorText
                            LoginFields.PASSWORD -> passwordError.value = errorModel.errorText
                            null -> showSnackbar(SnackbarResultType.ERROR, errorModel.errorText)
                        }
                    }
                },
                onComplete = {
                    showSnackbar(SnackbarResultType.SUCCESS, resourceProvider.getString(R.string.registration_result_success))
                    clearFields.value = true
                }
            )
            .addTo(disposables)
    }

    private fun showLoader(){
        showLoader.value = true
    }

    private fun hideLoader(){
        showLoader.value = false
    }

    private fun showSnackbar(type: SnackbarResultType, text: String){
        snackbarResult.value = SnackbarResultModel(type.color, text)
    }

}