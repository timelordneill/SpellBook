package com.example.spellbook.domain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
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

    fun fromDatabaseSpellbook(spellbook:DatabaseSpellbook):Spellbook{
        val spellViewmodel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)
        var convertedBook=Spellbook(spellbook.id, spellbook.name, mutableListOf(), mutableListOf())

        val characterClasses= mutableListOf<CharacterClass>()
        spellbook.characterClasses.split(";").forEach {
            val classString=it.split(",")
            characterClasses.add(CharacterClass(stringToClasses(classString[0]),classString[1].toInt()))
        }

        convertedBook.characterClass=characterClasses

        if(spellbook.spells.isNotEmpty() && spellbook.spells != ""){
            spellViewmodel.getSpells().observe(activity, Observer {
                val spellsFromString= mutableListOf<Spell>()
                spellbook.spells.split(";").forEach {string ->
                    spellsFromString.add(it!!.filter { spell ->
                        spell.name==string
                    }[0])
                }

                convertedBook.spells=spellsFromString
            })
        }

        return convertedBook
    }

    fun fromDatabaseSpellbooks(spellbooks:MutableList<DatabaseSpellbook>):MutableList<Spellbook>{
        val spellViewmodel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)
        val convertedSpellbooks= mutableListOf<Spellbook>()
        spellbooks.forEach { spellbook ->

            val characterClasses= mutableListOf<CharacterClass>()
            spellbook.characterClasses.split(";").forEach {
                val classString=it.split(",")
                characterClasses.add(CharacterClass(stringToClasses(classString[0]),classString[1].toInt()))
            }

            if(spellbook.spells.isNotEmpty() && spellbook.spells != ""){
                spellViewmodel.getSpells().observe(activity!!, Observer {
                    val spellsFromString= mutableListOf<Spell>()
                    spellbook.spells.split(";").forEach {string ->
                        spellsFromString.add(it!!.filter { spell ->
                            spell.name==string
                        }[0])
                    }

                    convertedSpellbooks.add(Spellbook(spellbook.id, spellbook.name, characterClasses, spellsFromString))
                })
            }else{
                convertedSpellbooks.add(Spellbook(spellbook.id, spellbook.name, characterClasses, mutableListOf()))
            }
        }

        return convertedSpellbooks
    }

    /**
     * converts string to [Classes]
     */
    private fun stringToClasses(string:String):Classes{
        when (string) {
            "Bard" -> return Classes.Bard
            "Wizard" -> return Classes.Wizard
            "Warlock" -> return Classes.Warlock
            "Paladin" -> return Classes.Paladin
            "Ranger" -> return Classes.Ranger
            "Druid" -> return Classes.Druid
            "Cleric" -> return Classes.Cleric
            "Sorcerer" -> return Classes.Sorcerer
        }

        return Classes.Bard
    }
}