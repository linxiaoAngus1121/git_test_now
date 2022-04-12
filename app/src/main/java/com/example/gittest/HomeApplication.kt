package com.example.gittest

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 *
 * 请描述类的作用
 * @author linxiao date: 2022年4月12日, 0012 14:27
 */
class HomeApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(appModel)
        }
    }
}