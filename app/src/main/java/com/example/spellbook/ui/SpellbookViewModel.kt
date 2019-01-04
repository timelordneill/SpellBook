package com.example.spellbook.ui

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Database
import com.example.spellbook.App
import com.example.spellbook.domain.DatabaseSpellbook
import com.example.spellbook.domain.Spellbook
import com.example.spellbook.domain.SpellbookRepository
import javax.inject.Inject

class SpellbookViewModel {
    @Inject
    lateinit var spellbookRepository: SpellbookRepository

    init {
        App.component.inject(this)
    }

    val allWords: LiveData<List<DatabaseSpellbook>> = spellbookRepository.spellbooks

    fun insert(spellbook: DatabaseSpellbook) {
        spellbookRepository.insert(spellbook)
    }
}