package id.idham.catalogue.di

import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.local.LocalDataSource
import id.idham.catalogue.data.remote.source.RemoteDataSource
import id.idham.catalogue.ui.detail.DetailMovieViewModel
import id.idham.catalogue.ui.favorite.movie.FavoriteMovieViewModel
import id.idham.catalogue.ui.favorite.tvshow.FavoriteTvShowViewModel
import id.idham.catalogue.ui.movie.MovieViewModel
import id.idham.catalogue.ui.tvshow.TvShowViewModel
import id.idham.catalogue.utils.ContextProviders
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailMovieViewModel(get()) }
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
}

val repositoryModule = module {
    single { ContextProviders.getInstance() }
    single { RemoteDataSource(get(), get()) }
    single { LocalDataSource(get(), get()) }
    single { CatalogueRepository(get(), get(), get()) }
}