package id.idham.catalogue.ui.favorite.movie

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueDataSource

class FavoriteMovieViewModel(private val catalogueDataSource: CatalogueDataSource) : ViewModel() {

    fun getFavoriteMovies() = catalogueDataSource.getFavoriteMovies()

}