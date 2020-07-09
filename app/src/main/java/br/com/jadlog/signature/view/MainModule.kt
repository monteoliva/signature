package br.com.jadlog.signature.view

import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

object MainModule {
    private val mainModule = module {
        viewModel { MainViewModel() }
    }

    fun loadModule() {
        loadKoinModules(
                listOf(mainModule)
        )
    }
}