package com.fiap.fiap_android_seguros.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiap.fiap_android_seguros.application.usecases.GetMinAppVersionUseCase
import com.fiap.fiap_android_seguros.data.remote.RequestState
import kotlinx.coroutines.launch

class BaseViewModel(
    private val getMinAppVersionUseCase: GetMinAppVersionUseCase
) : ViewModel() {


    var minVersionAppState = MutableLiveData<RequestState<Int>>()

    fun getMinVersion() {
        viewModelScope.launch {
            minVersionAppState.value = getMinAppVersionUseCase.getMinVersionApp()
        }
    }
}