package com.example.gittest

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 14:24
 */
val model= module {
    single {
        MainRepository()
    }

    viewModel {
        MainViewModel(get())
    }
}
val appModel = listOf(model)