package br.com.jadlog.signature

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import br.com.jadlog.signature.view.main.MainModule

class SignatureApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@SignatureApplication)
        }
        MainModule.loadModule()
    }
}