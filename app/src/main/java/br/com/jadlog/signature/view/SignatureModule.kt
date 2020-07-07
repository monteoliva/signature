package br.com.jadlog.signature.view

import br.com.jadlog.signature.ui.AssinaturaModule
import org.koin.core.context.loadKoinModules

object SignatureModule {
    fun loadModule() {
        loadKoinModules(
                listOf(  AssinaturaModule.execute() )
        )
    }
}