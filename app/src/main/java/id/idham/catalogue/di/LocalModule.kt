package id.idham.catalogue.di

import androidx.room.Room
import id.idham.catalogue.data.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().tvShowDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "catalogue.db"
        ).fallbackToDestructiveMigration().build()
    }
}