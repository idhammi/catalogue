package id.idham.catalogue.data.remote.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.utils.ContextProviders

class TvShowsDataSourceFactory(
    private val apiService: ApiService,
    private val contextProviders: ContextProviders
) : DataSource.Factory<Int, TvShowModel>() {

    val source = MutableLiveData<TvShowsDataSource>()

    override fun create(): DataSource<Int, TvShowModel> {
        val source = TvShowsDataSource(apiService, contextProviders)
        this.source.postValue(source)
        return source
    }

}