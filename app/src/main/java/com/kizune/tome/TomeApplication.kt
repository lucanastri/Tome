package com.kizune.tome

import android.app.Application
import com.kizune.tome.data.AppContainer
import com.kizune.tome.data.DefaultAppContainer

class TomeApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}