package id.idham.catalogue.data.source.remote

import id.idham.catalogue.data.source.remote.endpoint.ApiService

class RemoteRepository(private val apiService: ApiService) {

    suspend fun getMovies() = apiService.getMoviesPopularAsync().await()

    suspend fun getMovieDetail(movieId: Int) = apiService.getMovieDetailAsync(movieId).await()

    suspend fun getTvShows() = apiService.getTvShowsPopularAsync().await()

    suspend fun getTvShowDetail(tvId: Int) = apiService.getTvShowDetailAsync(tvId).await()

}