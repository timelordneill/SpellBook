package com.example.spellbook.database


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.spellbook.domain.DatabaseSpellbook

/**
 * [SpellbookDao] is the dao where queries to [SpellbookDatabase] are made
 */
@Dao
interface SpellbookDao {

    /**
     * Get all locally stored spellbooks
     */
    @Query("SELECT * from spellbook_table")
    fun getAllSpellbooks(): LiveData<List<DatabaseSpellbook>>

    /**
     * Locally save a new spellbook
     */
    @Insert
    fun insert(spellbook: DatabaseSpellbook)

    /**
     * Update an altered spellbook
     */
    @Update
    fun update(spellbook: DatabaseSpellbook)

    /**
     * Dalate a spellbook
     */
    @Delete
    fun delete(spellbook: DatabaseSpellbook)

    /**
     * Delete all locally stored spellbooks
     */
    @Query("DELETE FROM spellbook_table")
    fun deleteAll()
}