package com.example.spellbook.database


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.example.spellbook.domain.DatabaseSpellbook
import com.example.spellbook.domain.Spellbook

@Dao
interface SpellbookDao {

    @Query("SELECT * from spellbook_table")
    fun getAllSpellbooks(): LiveData<List<DatabaseSpellbook>>

    @Insert
    fun insert(spellbook: DatabaseSpellbook)

    @Update
    fun update(spellbook: DatabaseSpellbook)

    @Query("DELETE FROM spellbook_table")
    fun deleteAll()
}