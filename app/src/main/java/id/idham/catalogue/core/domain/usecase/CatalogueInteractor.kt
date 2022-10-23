package id.idham.catalogue.core.domain.usecase

import androidx.paging.PagingData
import id.idham.catalogue.core.data.Resource
import id.idham.catalogue.core.domain.model.Movie
import id.idham.catalogue.core.domain.model.TvShow
import id.idham.catalogue.core.domain.repository.ICatalogueRepository
import kotlinx.coroutines.flow.Flow

class CatalogueInteractor(private val repository: ICatalogueRepository) : CatalogueUseCase {

    override fun getMovies(): Flow<PagingData<Movie>> = repository.getMovies()

    override fun getMovieDetail(id: Int): Flow<Resource<Movie?>> = repository.getMovieDetail(id)

    override fun getTvShows(): Flow<PagingData<TvShow>> = repository.getTvShows()

    override fun getTvShowDetail(id: Int): Flow<Resource<TvShow?>> = repository.getTvShowDetail(id)

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> =
        repository.getFavoriteMovies(sort)

    override fun setFavoriteMovie(movie: Movie, favorite: Boolean) =
        repository.setFavoriteMovie(movie, favorite)

    override fun getFavoriteTvShows(sort: String): Flow<List<TvShow>> =
        repository.getFavoriteTvShows(sort)

    override fun setFavoriteTvShow(tvShow: TvShow, favorite: Boolean) =
        repository.setFavoriteTvShow(tvShow, favorite)
}