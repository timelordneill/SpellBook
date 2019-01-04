package com.example.spellbook.injection.component

import com.example.spellbook.App
import com.example.spellbook.injection.module.DatabaseModule
import com.example.spellbook.ui.SpellbookViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(app: App)
    fun inject(spellbookViewModel: SpellbookViewModel)
}