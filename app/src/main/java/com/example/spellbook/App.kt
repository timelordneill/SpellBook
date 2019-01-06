package com.example.spellbook

import android.app.Application
import com.example.spellbook.injection.component.DaggerDatabaseComponent
import com.example.spellbook.injection.component.DatabaseComponent
import com.example.spellbook.injection.module.DatabaseModule

/**
 * Supplies the databaseComponent
 */
class App : Application() {
    companion object {
        lateinit var component: DatabaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}