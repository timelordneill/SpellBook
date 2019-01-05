package com.example.spellbook.database


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.spellbook.domain.DatabaseSpellbook

@Dao
interface SpellbookDao {

    @Query("SELECT * from spellbook_table")
    fun getAllSpellbooks(): LiveData<List<DatabaseSpellbook>>

    @Insert
    fun insert(spellbook: DatabaseSpellbook)

    @Update
    fun update(spellbook: DatabaseSpellbook)

    @Delete
    fun delete(spellbook: DatabaseSpellbook)

    @Query("DELETE FROM spellbook_table")
    fun deleteAll()
}