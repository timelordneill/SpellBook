package com.example.spellbook.domain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.example.spellbook.R
import com.example.spellbook.ui.SpellViewmodel
import com.example.spellbook.ui.SpellbookViewModel

/**
 * [DatabaseSpellbookConverter] converts [Spellbook] to [DatabaseSpellbook]
 */
class DatabaseSpellbookConverter(val activity:FragmentActivity? = null){

    fun toDatabaseSpellbook(spellbook:Spellbook):DatabaseSpellbook{
        var classes=""
        spellbook.characterClass.forEach {
            classes=classes+it.name.className+","+it.level.toString()+";"
        }
        classes=classes.removeSuffix(";")

        var spells=""
        spellbook.spells!!.forEach {
            spells=spells+it.name+";"
        }
        spells=spells.removeSuffix(";")

        return DatabaseSpellbook(spellbook.id, spellbook.name, classes, spells)
    }
}