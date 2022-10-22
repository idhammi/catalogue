package id.idham.catalogue.data.remote.source

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }

    private val config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
    )

    fun getMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                MoviesPagingSource(service = apiService)
            }
        ).flow
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<MovieModel>> {
        return flow {
            try {
                val response = apiService.getMovieDetailAsync(id)
                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                emit(ApiResponse.Error(t.message.toString()))
                Log.e(this@RemoteDataSource::class.java.name, t.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTvShows(): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                TvShowsPagingSource(service = apiService)
            }
        ).flow
    }

    fun getTvShowDetail(id: Int): Flow<ApiResponse<TvShowModel>> {
        return flow {
            try {
                val response = apiService.getTvShowDetailAsync(id)
                emit(ApiResponse.Success(response))
            } catch (t: Throwable) {
                emit(ApiResponse.Error(t.message.toString()))
                Log.e(this@RemoteDataSource::class.java.name, t.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}