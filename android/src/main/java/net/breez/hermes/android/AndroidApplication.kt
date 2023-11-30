package net.breez.hermes.android

import android.app.Application
import net.breez.hermes.android.di.viewModelModules
import net.breez.hermes.data.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidApplication)
            modules(viewModelModules)
        }
    }
}