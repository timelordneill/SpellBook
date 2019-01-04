package com.example.spellbook.injection.module

import android.app.Application
import android.content.Context
import com.example.spellbook.database.SpellbookDao
import com.example.spellbook.database.SpellbookDatabase
import com.example.spellbook.domain.SpellbookRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Application) {
    @Provides
    @Singleton
    internal fun provideWordRepository(wordDao: SpellbookDao): SpellbookRepository {
        return SpellbookRepository(wordDao)
    }

    @Provides
    @Singleton
    internal fun provideWordDao(spellbookDatabase: SpellbookDatabase): SpellbookDao {
        return spellbookDatabase.spellbookDao()
    }

    @Provides
    @Singleton
    internal fun provideWordDatabase(context: Context): SpellbookDatabase {
        return SpellbookDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }
}