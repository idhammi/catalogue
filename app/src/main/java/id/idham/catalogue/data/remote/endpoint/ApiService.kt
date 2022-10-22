package id.idham.catalogue.data.remote.endpoint

import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.MovieResponse
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.data.remote.response.TvShowResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movies
    @GET("movie/popular")
    suspend fun getMoviesPopularAsync(
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetailAsync(
        @Path("movieId") movieId: Int
    ): MovieModel

    // TV Shows
    @GET("tv/popular")
    fun getTvShowsPopularAsync(
        @Query("page") page: Int
    ): Deferred<TvShowResponse>

    @GET("tv/{tvId}")
    fun getTvShowDetailAsync(
        @Path("tvId") tvId: Int
    ): Deferred<TvShowModel>

}