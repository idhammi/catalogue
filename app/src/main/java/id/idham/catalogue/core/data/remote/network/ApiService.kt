package id.idham.catalogue.core.data.remote.network

import id.idham.catalogue.core.data.remote.response.MovieResponse
import id.idham.catalogue.core.data.remote.response.ListMovieResponse
import id.idham.catalogue.core.data.remote.response.TvShowResponse
import id.idham.catalogue.core.data.remote.response.ListTvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Movies
    @GET("movie/popular")
    suspend fun getMoviesPopularAsync(
        @Query("page") page: Int
    ): ListMovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetailAsync(
        @Path("movieId") movieId: Int
    ): MovieResponse

    // TV Shows
    @GET("tv/popular")
    suspend fun getTvShowsPopularAsync(
        @Query("page") page: Int
    ): ListTvShowResponse

    @GET("tv/{tvId}")
    suspend fun getTvShowDetailAsync(
        @Path("tvId") tvId: Int
    ): TvShowResponse

}