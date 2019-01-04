package com.example.spellbook.domain

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread
import com.example.spellbook.database.SpellbookDao
import org.jetbrains.anko.doAsync

class SpellbookRepository(private val spellbookDao: SpellbookDao) {
    val spellbooks: LiveData<List<DatabaseSpellbook>> = spellbookDao.getAllSpellbooks()

    @WorkerThread
    fun insert(spellbook: DatabaseSpellbook) {
        doAsync {
            spellbookDao.insert(spellbook)
        }
    }

    @WorkerThread
    fun update(spellbook: DatabaseSpellbook) {
        doAsync {
            spellbookDao.update(spellbook)
        }
    }

    @WorkerThread
    fun deleteAll() {
        doAsync {
            spellbookDao.deleteAll()
        }
    }
}