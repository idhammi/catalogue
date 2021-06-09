package id.idham.catalogue.data.remote.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.vo.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class TvShowsDataSource(
    private val apiService: ApiService,
    private val coroutineContext: ContextProviders,
) : PageKeyedDataSource<Int, TvShowModel>() {

    private var supervisorJob = SupervisorJob()
    private var lastPage = false
    val networkState = MutableLiveData<Resource<TvShowModel>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TvShowModel>
    ) {
        executeQuery(1) { data -> callback.onResult(data, null, 2) }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TvShowModel>) {
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TvShowModel>) {
        val page = params.key
        executeQuery(page) { data -> callback.onResult(data, page + 1) }
    }

    private fun executeQuery(
        page: Int,
        callback: (List<TvShowModel>) -> Unit
    ) {
        if (!lastPage) {
            val loadingState = if (page == 1) "1" else null
            networkState.postValue(Resource.loading(msg = loadingState))
            GlobalScope.launch(coroutineContext.IO + supervisorJob) {
                try {
                    val response = apiService.getTvShowsPopularAsync(page).await()

                    lastPage = response.totalPages == page || response.results?.isEmpty() == true
                    if (page == 1 && lastPage) networkState.postValue(
                        Resource.error("Data not found")
                    ) else networkState.postValue(Resource.success(null))

                    val list = mutableListOf<TvShowModel>()
                    response.results?.forEach { results ->
                        results.let { list.add(it) }
                    }
                    callback(list)
                } catch (t: Throwable) {
                    networkState.postValue(Resource.error(t.message, null, t))
                }
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

}