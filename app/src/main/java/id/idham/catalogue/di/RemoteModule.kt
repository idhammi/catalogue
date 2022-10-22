package id.idham.catalogue.di

import id.idham.catalogue.data.remote.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val remoteModule = module {
    single { provideHttpLoggingInterceptor() }
    single { providesApiKey() }
    single { providesHttpClient(androidContext()) }
    single { provideMoshiConverter() }
    single { provideApiClient(get(), get()) }
}