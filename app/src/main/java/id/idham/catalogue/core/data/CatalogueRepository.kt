package id.idham.catalogue.core.data

import androidx.paging.PagingData
import id.idham.catalogue.core.data.local.LocalDataSource
import id.idham.catalogue.core.data.mapper.MovieMapper
import id.idham.catalogue.core.data.mapper.TvShowMapper
import id.idham.catalogue.core.data.remote.RemoteDataSource
import id.idham.catalogue.core.data.remote.network.ApiResponse
import id.idham.catalogue.core.data.remote.response.MovieResponse
import id.idham.catalogue.core.data.remote.response.TvShowResponse
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow
import id.idham.catalogue.core.domain.repository.ICatalogueRepository
import id.idham.catalogue.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatalogueRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : ICatalogueRepository {

    override fun getMovies(): Flow<PagingData<Movie>> {
        return remoteDataSource.getMovies().map {
            MovieMapper.mapResponseToDomain(it)
        }
    }

    override fun getMovieDetail(id: Int): Flow<Resource<Movie?>> =
        object : NetworkBoundResource<Movie?, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie?> =
                localDataSource.getMovieById(id).map {
                    MovieMapper.mapEntityToDomain(it)
                }

            override fun shouldFetch(data: Movie?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(id)

            override suspend fun saveCallResult(data: MovieResponse) {
                localDataSource.insertMovie(MovieMapper.mapResponseToEntity(data))
            }
        }.asFlow()

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies(sort).map {
            MovieMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, favorite: Boolean) {
        val entity = MovieMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(entity, favorite) }
    }

    override fun getTvShows(): Flow<PagingData<TvShow>> {
        return remoteDataSource.getTvShows().map {
            TvShowMapper.mapResponseToDomain(it)
        }
    }

    override fun getTvShowDetail(id: Int): Flow<Resource<TvShow?>> =
        object : NetworkBoundResource<TvShow?, TvShowResponse>() {
            override fun loadFromDB(): Flow<TvShow?> =
                localDataSource.getTvShowById(id).map {
                    TvShowMapper.mapEntityToDomain(it)
                }

            override fun shouldFetch(data: TvShow?): Boolean =
                data == null

            override suspend fun createCall(): Flow<ApiResponse<TvShowResponse>> =
                remoteDataSource.getTvShowDetail(id)

            override suspend fun saveCallResult(data: TvShowResponse) {
                localDataSource.insertTvShow(TvShowMapper.mapResponseToEntity(data))
            }
        }.asFlow()

    override fun getFavoriteTvShows(sort: String): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTvShows(sort).map {
            TvShowMapper.mapEntityToDomain(it)
        }
    }

    override fun setFavoriteTvShow(tvShow: TvShow, favorite: Boolean) {
        val entity = TvShowMapper.mapDomainToEntity(tvShow)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(entity, favorite) }
    }

}