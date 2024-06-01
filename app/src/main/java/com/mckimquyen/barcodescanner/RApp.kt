package com.mckimquyen.barcodescanner

import androidx.multidex.MultiDexApplication
import com.mckimquyen.barcodescanner.di.settings
import com.mckimquyen.barcodescanner.usecase.Logger
import io.reactivex.plugins.RxJavaPlugins


//TODO ad applovin
//TODO finger print
//TODO why you see ad
//TODO import oplm vietnam

//TODO change icon launcher
//TODO double to exit app
//TODO leak canary
//TODO proguard
//TODO change pkg name manifest
//TODO keystore

//done mckimquyen
//github
//policy
//rate app, share app, more app
//build version

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
