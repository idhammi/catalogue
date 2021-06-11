package id.idham.catalogue.data.remote.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.utils.EspressoIdlingResource
import id.idham.catalogue.vo.Pagination
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RemoteDataSource(
    private val apiService: ApiService, private val coroutineContext: ContextProviders
) {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setPageSize(PAGE_SIZE)
        .build()

    fun getMovies(): Pagination<MovieModel> {
        val factory = MoviesDataSourceFactory(apiService, coroutineContext)
        val networkState =
            Transformations.switchMap(factory.source, MoviesDataSource::networkState)
        return Pagination(LivePagedListBuilder(factory, config).build(), networkState)
    }

    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieModel>> {
        EspressoIdlingResource.increment() // for instrumentation test only
        val result = MutableLiveData<ApiResponse<MovieModel>>()
        GlobalScope.launch(coroutineContext.Main) {
            try {
                result.value = ApiResponse.success(
                    apiService.getMovieDetailAsync(id).await()
                )
                EspressoIdlingResource.decrement() // for instrumentation test only
            } catch (t: Throwable) {
                result.value = ApiResponse.error(t.message.toString())
            }
        }
        return result
    }

    fun getTvShows(): Pagination<TvShowModel> {
        val factory = TvShowsDataSourceFactory(apiService, coroutineContext)
        val networkState =
            Transformations.switchMap(factory.source, TvShowsDataSource::networkState)
        return Pagination(LivePagedListBuilder(factory, config).build(), networkState)
    }

    fun getTvShowDetail(id: Int): LiveData<ApiResponse<TvShowModel>> {
        EspressoIdlingResource.increment() // for instrumentation test only
        val result = MutableLiveData<ApiResponse<TvShowModel>>()
        GlobalScope.launch(coroutineContext.Main) {
            try {
                result.value = ApiResponse.success(
                    apiService.getTvShowDetailAsync(id).await()
                )
                EspressoIdlingResource.decrement() // for instrumentation test only
            } catch (t: Throwable) {
                result.value = ApiResponse.error(t.message.toString())
            }
        }
        return result
    }

}