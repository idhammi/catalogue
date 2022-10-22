package id.idham.catalogue.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.idham.catalogue.data.remote.endpoint.ApiService
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.source.RemoteDataSource.Companion.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

class MoviesPagingSource(private val service: ApiService) : PagingSource<Int, MovieModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
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

    companion object {
        private const val TMDB_STARTING_PAGE_INDEX = 1
    }
}