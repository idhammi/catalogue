package id.idham.catalogue.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.idham.catalogue.core.domain.usecase.CatalogueUseCase

class TvShowViewModel(useCase: CatalogueUseCase) : ViewModel() {

    val tvShows = useCase.getTvShows().cachedIn(viewModelScope)

}