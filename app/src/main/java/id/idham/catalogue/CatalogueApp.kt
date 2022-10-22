package id.idham.catalogue

import android.app.Application
import id.idham.catalogue.di.localModule
import id.idham.catalogue.di.remoteModule
import id.idham.catalogue.di.repositoryModule
import id.idham.catalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CatalogueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CatalogueApp)
            modules(
                listOf(
                    viewModelModule,
                    repositoryModule,
                    remoteModule,
                    localModule
                )
            )
        }
    }
}