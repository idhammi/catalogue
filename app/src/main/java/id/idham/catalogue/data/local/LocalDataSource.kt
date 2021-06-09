package id.idham.catalogue.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.idham.catalogue.data.local.dao.MovieDao
import id.idham.catalogue.data.local.dao.TvShowDao
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity

class LocalDataSource(private val movieDao: MovieDao, private val tvShowDao: TvShowDao) {

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = movieDao.getFavoriteMovies()

    fun getMovieById(id: Int): LiveData<MovieEntity> = movieDao.getMovieById(id)

    fun insertMovie(movie: MovieEntity) = movieDao.insert(movie)

    fun setFavoriteMovie(movie: MovieEntity, favorite: Boolean) {
        movie.favorite = favorite
        movieDao.update(movie)
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = tvShowDao.getFavoriteTvShows()

    fun getTvShowById(id: Int): LiveData<TvShowEntity> = tvShowDao.getTvShowById(id)

    fun insertTvShow(tvShows: TvShowEntity) = tvShowDao.insert(tvShows)

    fun setFavoriteTvShow(tvShow: TvShowEntity, favorite: Boolean) {
        tvShow.favorite = favorite
        tvShowDao.update(tvShow)
    }

}