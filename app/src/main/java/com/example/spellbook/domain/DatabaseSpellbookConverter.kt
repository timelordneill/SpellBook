package com.example.spellbook.domain

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.example.spellbook.R
import com.example.spellbook.ui.SpellViewmodel
import com.example.spellbook.ui.SpellbookViewModel

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

        return DatabaseSpellbook(0, spellbook.name, classes, spells)
    }

    fun fromDatabaseSpellbook(spellbooks:MutableList<DatabaseSpellbook>):MutableList<Spellbook>{
        val spellViewmodel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)
        var convertedSpellbooks= mutableListOf<Spellbook>()
        spellbooks.forEach { spellbook ->

            var characterClasses= mutableListOf<CharacterClass>()
            spellbook.characterClasses.split(";").forEach {
                var classString=it.split(",")
                characterClasses.add(CharacterClass(stringToClasses(classString[0]),classString[1].toInt()))
            }
            if(spellbook.spells.isNotEmpty() && spellbook.spells != ""){
                spellViewmodel.getSpells().observe(activity, Observer {
                    var spellsFromString= mutableListOf<Spell>()
                    spellbook.spells.split(";").forEach {string ->
                        spellsFromString.add(it!!.filter { spell ->
                            spell.name==string
                        }[0])
                    }

                    convertedSpellbooks.add(Spellbook(spellbook.name, characterClasses, spellsFromString))
                })
            }else{
                convertedSpellbooks.add(Spellbook(spellbook.name, characterClasses, mutableListOf()))
            }
        }

        return convertedSpellbooks
    }

    fun stringToClasses(string:String):Classes{
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