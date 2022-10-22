package id.idham.catalogue.data

import id.idham.catalogue.data.remote.ApiResponseFlow
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResourceFlow<ResultType, RequestType> {

    private var result: Flow<ResourceFlow<ResultType>> = flow {
        emit(ResourceFlow.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(ResourceFlow.Loading())
            when (val apiResponse = createCall().first()) {
                is ApiResponseFlow.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDB().map { ResourceFlow.Success(it) })
                }
                is ApiResponseFlow.Empty -> {
                    emitAll(loadFromDB().map { ResourceFlow.Success(it) })
                }
                is ApiResponseFlow.Error -> {
                    onFetchFailed()
                    emit(ResourceFlow.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { ResourceFlow.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<ApiResponseFlow<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ResourceFlow<ResultType>> = result
}
