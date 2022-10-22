package id.idham.catalogue.data.remote.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.ApiResponseFlow
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.vo.Pagination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RemoteDataSource(
    private val apiService: ApiService, private val coroutineContext: ContextProviders
) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(NETWORK_PAGE_SIZE)
        .setPageSize(NETWORK_PAGE_SIZE)
        .build()

    private val configNew = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
    )

    fun getMovies(): Pagination<MovieModel> {
        val factory = MoviesDataSourceFactory(apiService, coroutineContext)
        val networkState =
            Transformations.switchMap(factory.source, MoviesDataSource::networkState)
        return Pagination(LivePagedListBuilder(factory, config).build(), networkState)
    }

    fun getMoviesFlow(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = configNew,
            pagingSourceFactory = {
                MoviesPagingSource(service = apiService)
            }
        ).flow
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponseFlow<MovieModel>> {
        return flow {
            try {
                val response = apiService.getMovieDetailAsync(id)
                emit(ApiResponseFlow.Success(response))
            } catch (t: Throwable) {
                emit(ApiResponseFlow.Error(t.message.toString()))
                Log.e(this@RemoteDataSource::class.java.name, t.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShows(): Pagination<TvShowModel> {
        val factory = TvShowsDataSourceFactory(apiService, coroutineContext)
        val networkState =
            Transformations.switchMap(factory.source, TvShowsDataSource::networkState)
        return Pagination(LivePagedListBuilder(factory, config).build(), networkState)
    }

    fun getTvShowDetail(id: Int): LiveData<ApiResponse<TvShowModel>> {
        val result = MutableLiveData<ApiResponse<TvShowModel>>()
        GlobalScope.launch(coroutineContext.Main) {
            try {
                result.value = ApiResponse.success(
                    apiService.getTvShowDetailAsync(id).await()
                )
            } catch (t: Throwable) {
                result.value = ApiResponse.error(t.message.toString())
            }
        }
        return result
    }

}