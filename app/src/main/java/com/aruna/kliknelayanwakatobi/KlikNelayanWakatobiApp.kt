package com.aruna.kliknelayanwakatobi

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


/**
 * Created by marzellaalfamega on 12/8/17.
 */
class KlikNelayanWakatobiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("wakatobi.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

    companion object {
        val WAITING_TIME_GENERAL_MILLIS: Long = 500
        lateinit var instance: KlikNelayanWakatobiApp
            private set

    }

}