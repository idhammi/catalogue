package id.idham.catalogue.core.data.local

import id.idham.catalogue.core.data.local.entity.MovieEntity
import id.idham.catalogue.core.data.local.entity.TvShowEntity
import id.idham.catalogue.core.data.local.room.MovieDao
import id.idham.catalogue.core.data.local.room.TvShowDao
import id.idham.catalogue.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao, private val tvShowDao: TvShowDao) {

    fun getFavoriteMovies(sort: String): Flow<List<MovieEntity>> {
        val query = SortUtils.getSortedQuery(sort, "movie")
        return movieDao.getFavoriteMovies(query)
    }

    fun getMovieById(id: Int): Flow<MovieEntity> = movieDao.getMovieById(id)

    suspend fun insertMovie(movie: MovieEntity) = movieDao.insert(movie)

    fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean) {
        movie.isFavorite = favorite
        movieDao.update(movie)
    }

    fun getFavoriteTvShows(sort: String): Flow<List<TvShowEntity>> {
        val query = SortUtils.getSortedQuery(sort, "tvshow")
        return tvShowDao.getFavoriteTvShows(query)
    }

    fun getTvShowById(id: Int): Flow<TvShowEntity> = tvShowDao.getTvShowById(id)

    suspend fun insertTvShow(tvShows: TvShowEntity) = tvShowDao.insert(tvShows)

    fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean) {
        tvShow.isFavorite = favorite
        tvShowDao.update(tvShow)
    }

}