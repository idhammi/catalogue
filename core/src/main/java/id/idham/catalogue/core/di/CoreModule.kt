package id.idham.catalogue.core.di

import androidx.room.Room
import id.idham.catalogue.core.data.CatalogueRepository
import id.idham.catalogue.core.data.local.LocalDataSource
import id.idham.catalogue.core.data.local.room.AppDatabase
import id.idham.catalogue.core.data.remote.RemoteDataSource
import id.idham.catalogue.core.data.remote.network.provideApiClient
import id.idham.catalogue.core.data.remote.network.provideMoshiConverter
import id.idham.catalogue.core.data.remote.network.providesHttpClient
import id.idham.catalogue.core.domain.repository.ICatalogueRepository
import id.idham.catalogue.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    factory { get<AppDatabase>().movieDao() }
    factory { get<AppDatabase>().tvShowDao() }
    single {
        val passphrase = SQLiteDatabase.getBytes("movieCatalogue".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java, "catalogue.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val remoteModule = module {
    single { providesHttpClient(androidContext()) }
    single { provideMoshiConverter() }
    single { provideApiClient(get(), get()) }
}

val repositoryModule = module {
    factory { AppExecutors() }
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get()) }
    single<ICatalogueRepository> { CatalogueRepository(get(), get(), get()) }
}