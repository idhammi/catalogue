package id.idham.catalogue.data.source.remote.endpoint

import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.MovieResponse
import id.idham.catalogue.data.source.remote.response.TvShowModel
import id.idham.catalogue.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    // Movies
    @GET("movie/popular")
    fun getMoviesPopularAsync(): Deferred<MovieResponse>

    @GET("movie/{movieId}")
    fun getMovieDetailAsync(@Path("movieId") movieId: Int): Deferred<MovieModel>

    // TV Shows
    @GET("tv/popular")
    fun getTvShowsPopularAsync(): Deferred<TvShowResponse>

    @GET("tv/{tvId}")
    fun getTvShowDetailAsync(@Path("tvId") tvId: Int): Deferred<TvShowModel>

}