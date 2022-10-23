package id.idham.catalogue.core.data.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.idham.catalogue.core.data.remote.network.ApiResponse
import id.idham.catalogue.core.data.remote.network.ApiService
import id.idham.catalogue.core.data.remote.response.MovieResponse
import id.idham.catalogue.core.data.remote.response.TvShowResponse
import id.idham.catalogue.core.data.remote.source.MoviesPagingSource
import id.idham.catalogue.core.data.remote.source.TvShowsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private val config = PagingConfig(
        pageSize = NETWORK_PAGE_SIZE,
        enablePlaceholders = false
    )

    fun getMovies(): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                MoviesPagingSource(service = apiService)
            }
        ).flow
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<MovieResponse>> {
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

    fun getTvShows(): Flow<PagingData<TvShowResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                TvShowsPagingSource(service = apiService)
            }
        ).flow
    }

    fun getTvShowDetail(id: Int): Flow<ApiResponse<TvShowResponse>> {
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

    companion object {
        const val NETWORK_PAGE_SIZE = 10
        const val TMDB_STARTING_PAGE_INDEX = 1
    }

}