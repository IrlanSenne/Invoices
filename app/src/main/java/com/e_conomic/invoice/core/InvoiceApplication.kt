package com.e_conomic.invoice.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class InvoiceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}