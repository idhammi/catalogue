package id.idham.catalogue

import android.app.Application
import id.idham.catalogue.core.di.localModule
import id.idham.catalogue.core.di.remoteModule
import id.idham.catalogue.core.di.repositoryModule
import id.idham.catalogue.di.useCaseModule
import id.idham.catalogue.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class CatalogueApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@CatalogueApp)
            modules(
                listOf(
                    localModule,
                    remoteModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}