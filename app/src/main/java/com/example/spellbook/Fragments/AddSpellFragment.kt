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

    /**
     * Adds adapter to recyclerview and checks if spellbooks are correct class
     */
    override fun onStart() {
        super.onStart()

        spellbookViewmodel.allSpellbooks.observe(this, Observer {
            val converter=DatabaseSpellbookConverter(activity)
            val convertedBooks=converter.fromDatabaseSpellbooks(it!! as MutableList<DatabaseSpellbook>)
            val correctClasses= mutableListOf<Spellbook>()
            convertedBooks.forEach { book ->
                book.characterClass.forEach{characterclass ->
                    if(spell.classes.contains(characterclass.name.className.toLowerCase())){
                        correctClasses.add(book)
                    }
                }
            }
            spellbook_list.adapter=AddSpellRecyclerViewAdapter(this, correctClasses)
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
}
