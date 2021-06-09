package id.idham.catalogue.di

import id.idham.catalogue.data.local.AppDatabase
import id.idham.catalogue.data.local.dao.MovieDao
import id.idham.catalogue.data.local.dao.TvShowDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {
    fun movieDao(database: AppDatabase): MovieDao = database.movie()
    fun tvShowDao(database: AppDatabase): TvShowDao = database.tvShow()

    single { AppDatabase.getInstance(androidApplication()) }
    single { movieDao(get()) }
    single { tvShowDao(get()) }
}