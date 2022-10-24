package id.idham.catalogue.di

import id.idham.catalogue.core.domain.usecase.CatalogueInteractor
import id.idham.catalogue.core.domain.usecase.CatalogueUseCase
import id.idham.catalogue.detail.DetailMovieViewModel
import id.idham.catalogue.movie.MovieViewModel
import id.idham.catalogue.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<CatalogueUseCase> { CatalogueInteractor(get()) }
}

val viewModelModule = module {
    viewModel { DetailMovieViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
}