package id.idham.catalogue.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.idham.catalogue.core.domain.usecase.CatalogueUseCase

class MovieViewModel(useCase: CatalogueUseCase) : ViewModel() {

    val movies = useCase.getMovies().cachedIn(viewModelScope)

}