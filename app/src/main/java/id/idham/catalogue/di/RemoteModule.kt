package id.idham.catalogue.di

import id.idham.catalogue.data.source.remote.*
import org.koin.dsl.module

val remoteModule = module {
    single { provideHttpLoggingInterceptor() }
    single { providesApiKey() }
    single { providesHttpClient() }
    single { provideMoshiConverter() }
    single { provideApiClient(get(), get()) }
}