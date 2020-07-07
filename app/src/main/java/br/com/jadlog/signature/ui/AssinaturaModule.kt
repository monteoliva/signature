package br.com.jadlog.signature.ui

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AssinaturaModule {
    private val assinaturaModule = module {
        viewModel { AssinaturaViewModel() }
    }

    fun execute(): Module {
        return assinaturaModule
    }
}