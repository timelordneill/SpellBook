package com.example.spellbook.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.spellbook.domain.DatabaseSpellbook

/**
 * [SpellbookDatabase] is the database where all locally saved spellbooks are stored
 */
@Database(entities = [DatabaseSpellbook::class], version = 1, exportSchema = false)
abstract class SpellbookDatabase : RoomDatabase() {
    abstract fun spellbookDao(): SpellbookDao

    companion object {
        @Volatile
        private var INSTANCE: SpellbookDatabase? = null
        const val DATABASE_NAME = "spellbook_database"

        fun getDatabase(context: Context): SpellbookDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpellbookDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(object : RoomDatabase.Callback() {
                })
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}