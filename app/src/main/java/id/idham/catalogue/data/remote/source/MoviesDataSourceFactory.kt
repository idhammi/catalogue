package id.idham.catalogue.data.remote.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.utils.ContextProviders

class MoviesDataSourceFactory(
    private val apiService: ApiService,
    private val contextProviders: ContextProviders
) : DataSource.Factory<Int, MovieModel>() {

    val source = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, MovieModel> {
        val source = MoviesDataSource(apiService, contextProviders)
        this.source.postValue(source)
        return source
    }

}