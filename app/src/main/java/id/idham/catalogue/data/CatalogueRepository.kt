package id.idham.catalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.idham.catalogue.data.local.LocalDataSource
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.mapper.MovieMapper
import id.idham.catalogue.data.mapper.TvShowMapper
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.data.remote.source.RemoteDataSource
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.vo.Pagination
import id.idham.catalogue.vo.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val contextProviders: ContextProviders
) : CatalogueDataSource {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(PAGE_SIZE)
        .setPageSize(PAGE_SIZE)
        .build()

    override fun getMovies(): Pagination<MovieModel> {
        return remoteDataSource.getMovies()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieModel>(contextProviders) {
            override fun loadFromDb(): LiveData<MovieEntity> =
                localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<MovieModel>> =
                remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(data: MovieModel?) {
                data?.let { localDataSource.insertMovie(MovieMapper.mapResponseToEntity(data)) }
            }
        }.asLiveData()
    }

    override fun getTvShows(): Pagination<TvShowModel> {
        return remoteDataSource.getTvShows()
    }

    override fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowModel>(contextProviders) {
            override fun loadFromDb(): LiveData<TvShowEntity> =
                localDataSource.getTvShowById(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<TvShowModel>> =
                remoteDataSource.getTvShowDetail(id)

            override fun saveCallResult(data: TvShowModel?) {
                data?.let { localDataSource.insertTvShow(TvShowMapper.mapResponseToEntity(data)) }
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(sort: String, table: String): LiveData<PagedList<MovieEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(sort, table), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean) {
        GlobalScope.launch(contextProviders.IO) {
            localDataSource.setFavoriteMovie(movie, favorite)
        }
    }

    override fun getFavoriteTvShows(
        sort: String, table: String
    ): LiveData<PagedList<TvShowEntity>> {
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(sort, table), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean) {
        GlobalScope.launch(contextProviders.IO) {
            localDataSource.setFavoriteTvShow(tvShow, favorite)
        }
    }

}