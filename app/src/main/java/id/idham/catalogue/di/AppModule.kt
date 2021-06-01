package id.idham.catalogue.di

import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.Repository
import id.idham.catalogue.data.source.remote.RemoteRepository
import id.idham.catalogue.ui.detail.DetailMovieViewModel
import id.idham.catalogue.ui.movie.MovieViewModel
import id.idham.catalogue.ui.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailMovieViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
}

val repositoryModule = module {
    single { RemoteRepository(get()) }
    single { CatalogueRepository(get()) } binds arrayOf(Repository::class)
}