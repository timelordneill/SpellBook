package com.example.spellbook.Fragments

import android.os.Bundle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.domain.RecyclerViewAdapters.AddSpellRecyclerViewAdapter
import com.example.spellbook.ui.SpellViewmodel
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.fragment_add_spell.*

/**
 * [AddSpellFragment] shows a list of saved [Spellbook]s to add a spell to
 */
class AddSpellFragment : android.support.v4.app.Fragment() {

    private lateinit var  spellbookViewmodel: SpellbookViewModel
    private lateinit var spell: Spell

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spell, container, false)
    }

    override fun onStart() {
        super.onStart()

        spellbookViewmodel.allSpellbooks.observe(this, Observer {
            val converter=DatabaseSpellbookConverter(activity)
            spellbook_list.adapter=AddSpellRecyclerViewAdapter(this, fromDatabaseSpellbook(it!! as MutableList<DatabaseSpellbook>))
        })

        spellbook_list.layoutManager= LinearLayoutManager(activity)
    }

    /**
     * adds [spell] to add to [Spellbook] to fragment
     */
    fun addObject(spell:Spell){
        this.spell=spell
    }

    /**
     * adds [spell] to chosen [Spellbook]
     */
    fun addSpell(spellbook: Spellbook){
        spellbook.spells!!.add(spell)
        val converter=DatabaseSpellbookConverter(activity)
        val databasebook=converter.toDatabaseSpellbook(spellbook)
        spellbookViewmodel.update(databasebook)

        val spellbookFragment = SpellbookFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, spellbookFragment)
            .addToBackStack(null)
            .commit()
        spellbookFragment.addObject(spellbook)
    }

    /**
     * Converts [DatabaseSpellbook] to [Spellbook]
     */
    fun fromDatabaseSpellbook(spellbooks:MutableList<DatabaseSpellbook>):MutableList<Spellbook>{
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
