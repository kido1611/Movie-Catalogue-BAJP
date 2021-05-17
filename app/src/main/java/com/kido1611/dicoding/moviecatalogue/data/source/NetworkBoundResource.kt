package com.kido1611.dicoding.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kido1611.dicoding.moviecatalogue.data.source.remote.ApiResponse
import com.kido1611.dicoding.moviecatalogue.utils.AppExecutors


abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<UIState<ResultType>>()

    init {
        result.value = UIState.Loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = UIState.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = UIState.Loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is ApiResponse.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = UIState.Error(response.message, newData)
                    }
                }
                ApiResponse.Empty -> {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = UIState.Success(newData)
                    }
                }
                is ApiResponse.Success -> {
                    mExecutors.diskIO().execute {
                        saveCallResult(response.data)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = UIState.Success(newData)
                            }
                        }
                    }
                }
                ApiResponse.Loading -> {
                    // Do nothing
                }
            }
        }
    }

    fun asLiveData(): LiveData<UIState<ResultType>> = result
}