package id.idham.catalogue.ui.movie

import androidx.lifecycle.ViewModel
import id.idham.catalogue.data.CatalogueDataSource

class MovieViewModel(private val catalogueDataSource: CatalogueDataSource) : ViewModel() {

    fun getMovies() = catalogueDataSource.getMovies()

}