package id.idham.catalogue.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.idham.catalogue.data.CatalogueRepository

class MovieViewModel(private val repository: CatalogueRepository) : ViewModel() {

    fun getMovies() = repository.getMovies().cachedIn(viewModelScope)

}