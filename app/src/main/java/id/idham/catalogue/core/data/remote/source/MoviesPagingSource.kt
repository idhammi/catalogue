package id.idham.catalogue.core.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.idham.catalogue.core.data.remote.RemoteDataSource.Companion.NETWORK_PAGE_SIZE
import id.idham.catalogue.core.data.remote.RemoteDataSource.Companion.TMDB_STARTING_PAGE_INDEX
import id.idham.catalogue.core.data.remote.network.ApiService
import id.idham.catalogue.core.data.remote.response.MovieResponse
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(private val service: ApiService) : PagingSource<Int, MovieResponse>() {

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = service.getMoviesPopularAsync(pageIndex)
            val movies = response.results
            val nextKey = if (movies.isEmpty()) null else {
                pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

}