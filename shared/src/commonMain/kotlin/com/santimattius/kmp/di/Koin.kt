package com.santimattius.kmp.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun startKoinApplication(appModule: Module = module { }): KoinApplication {
    return startKoinApplication(
        listOf(appModule, platformModule, sharedModule, coreModule)
    )
}

fun startKoinApplication(modules: List<Module>): KoinApplication {
    return startKoin {
        modules(modules)
    }
}