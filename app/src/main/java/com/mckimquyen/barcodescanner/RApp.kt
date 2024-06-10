package com.mckimquyen.barcodescanner

import androidx.multidex.MultiDexApplication
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.usecase.Logger
import io.reactivex.plugins.RxJavaPlugins


//TODO ad applovin
//TODO finger print
//TODO why you see ad
//TODO double to exit app
//TODO ui switch
//TODO change icon launcher
//TODO leak canary
//TODO proguard
//TODO keystore

//done mckimquyen
//github
//policy
//rate app, share app, more app
//build version
//change pkg name manifest

class RApp : MultiDexApplication() {

    override fun onCreate() {
        handleUnhandledRxJavaErrors()
        applyTheme()
        super.onCreate()
    }

    private fun applyTheme() {
        settings.reapplyTheme()
    }

    private fun handleUnhandledRxJavaErrors() {
        RxJavaPlugins.setErrorHandler { error ->
            Logger.log(error)
        }
    }
}
