package com.example.spellbook.domain

import android.support.v4.app.FragmentActivity

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