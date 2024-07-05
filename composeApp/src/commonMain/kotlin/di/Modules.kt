package di

import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.viewModels.GOFViewModel

val commonModule = module {
    factory {
        GOFViewModel()
    }
}

expect val platformModule: Module
