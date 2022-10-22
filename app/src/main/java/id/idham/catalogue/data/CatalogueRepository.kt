package id.idham.catalogue.data

import androidx.paging.PagingData
import id.idham.catalogue.data.local.LocalDataSource
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.mapper.MovieMapper
import id.idham.catalogue.data.mapper.TvShowMapper
import id.idham.catalogue.data.remote.ApiResponse
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.data.remote.source.RemoteDataSource
import id.idham.catalogue.utils.AppExecutors
import kotlinx.coroutines.flow.Flow

class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : CatalogueDataSource {

    override fun getMovies(): Flow<PagingData<MovieModel>> {
        return remoteDataSource.getMovies()
    }

    override fun getMovieDetail(id: Int): Flow<Resource<MovieEntity>> =
        object : NetworkBoundResource<MovieEntity, MovieModel>() {
            override fun loadFromDB(): Flow<MovieEntity> =
                localDataSource.getMovieById(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<MovieModel>> =
                remoteDataSource.getMovieDetail(id)

            override suspend fun saveCallResult(data: MovieModel) {
                localDataSource.insertMovie(MovieMapper.mapResponseToEntity(data))
            }
        }.asFlow()

    override fun getTvShows(): Flow<PagingData<TvShowModel>> {
        return remoteDataSource.getTvShows()
    }

    override fun getTvShowDetail(id: Int): Flow<Resource<TvShowEntity>> =
        object : NetworkBoundResource<TvShowEntity, TvShowModel>() {
            override fun loadFromDB(): Flow<TvShowEntity> =
                localDataSource.getTvShowById(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowModel>> =
                remoteDataSource.getTvShowDetail(id)

            override suspend fun saveCallResult(data: TvShowModel) {
                localDataSource.insertTvShow(TvShowMapper.mapResponseToEntity(data))
            }
        }.asFlow()

    override fun getFavoriteMovies(sort: String): Flow<List<MovieEntity>> {
        return localDataSource.getFavoriteMovies(sort)
    }

    override fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean) {
        appExecutors.diskIO().execute() { localDataSource.setFavoriteMovie(movie, favorite) }
    }

    override fun getFavoriteTvShows(sort: String): Flow<List<TvShowEntity>> {
        return localDataSource.getFavoriteTvShows(sort)
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean) {
        appExecutors.diskIO().execute() { localDataSource.setFavoriteTvShow(tvShow, favorite) }
    }

}