package id.idham.catalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.StatusResponse
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.vo.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class NetworkBoundResource<ResultType, RequestType>(private val contextProviders: ContextProviders) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis") val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}
    protected abstract fun loadFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
    protected abstract fun saveCallResult(data: RequestType?)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    GlobalScope.launch(contextProviders.IO) {
                        saveCallResult(response.body)
                        GlobalScope.launch(contextProviders.Main) {
                            result.addSource(loadFromDb()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                }
                StatusResponse.EMPTY -> {
                    GlobalScope.launch(contextProviders.Main) {
                        result.addSource(loadFromDb()) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}
