package com.jacobgb24.healthhistory.viewmodels

import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jacobgb24.healthhistory.BaseApplication
import com.jacobgb24.healthhistory.api.ApiError
import com.jacobgb24.healthhistory.api.ApiInterface
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.combineData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class InfoViewModel @ViewModelInject constructor(
    private var api: ApiInterface,
    private val dispatcher: CoroutineContext
) : ViewModel() {


}