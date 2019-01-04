package com.example.spellbook.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "spellbook_table")
class DatabaseSpellbook (@PrimaryKey(autoGenerate = true)
                         val id:Int,
                         val name: String="",
                         val characterClasses:String="",
                         val spells:String="")
