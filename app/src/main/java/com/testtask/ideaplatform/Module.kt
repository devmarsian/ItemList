package com.testtask.ideaplatform

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { AppDatabase.getDatabase(get()).itemDao() }

    single { ItemRepositoryImpl(get()) }

    viewModel { ItemViewModel(get()) }
}
