package com.example.spellbook.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.spellbook.R
import com.example.spellbook.domain.DatabaseSpellbook
import com.example.spellbook.domain.DatabaseSpellbookConverter
import com.example.spellbook.domain.RecyclerViewAdapters.AddSpellRecyclerViewAdapter
import com.example.spellbook.domain.Spell
import com.example.spellbook.domain.Spellbook
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.fragment_add_spell.*
import kotlinx.android.synthetic.main.fragment_spell_detail.*
import kotlinx.android.synthetic.main.fragment_spell_detail.view.*

/**
 * [SpellDetailFragment] shows the details of a spell
 */
class SpellDetailFragment : android.support.v4.app.Fragment() {

    private lateinit var spell: Spell
    private lateinit var  spellbookViewmodel: SpellbookViewModel

    /**
     * Fills textboxes with [Spell] text
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        // Inflate the layout for this fragment
        val rootView=inflater.inflate(R.layout.fragment_spell_detail, container, false)

        spell.let {
            rootView.text_title.text=it.name
            rootView.text_type.text=it.type
            rootView.text_time.text=it.casting_time
            rootView.text_range.text=it.range
            rootView.text_components.text=it.components.raw
            rootView.text_duration.text=it.duration
            rootView.text_desc.text=it.description
            rootView.text_classes.text=it.classes.toString().replace("[", "").replace("]", "")
        }

        return rootView
    }

    /**
     * Sets clicklistener to [AddSpellFragment]
     */
    override fun onStart() {
        super.onStart()
        //test if there are any spellbooks able to add the spell, if not, show a toast
        add_button.setOnClickListener {
            spellbookViewmodel.allSpellbooks.observe(this, Observer {
                val converter= DatabaseSpellbookConverter(activity)
                val convertedBooks=converter.fromDatabaseSpellbooks(it!! as MutableList<DatabaseSpellbook>)
                val correctClasses= mutableListOf<Spellbook>()
                convertedBooks.forEach { book ->
                    book.characterClass.forEach{characterclass ->
                        if(spell.classes.contains(characterclass.name.className.toLowerCase())){
                            correctClasses.add(book)
                        }
                    }
                }

                if(correctClasses.isNullOrEmpty()){
                    Toast.makeText(getActivity(), "No spellbooks have the right class to add this spell", Toast.LENGTH_SHORT).show();
                }else{
                    val addSpellFragment = AddSpellFragment()
                    this.fragmentManager!!.beginTransaction()
                        .replace(R.id.list_frame, addSpellFragment)
                        .addToBackStack(null)
                        .commit()
                    addSpellFragment.addObject(spell)
                }
            })
        }
    }

    /**
     * adds spell to fragment
     */
    fun addObject(item: Spell){
        this.spell = item
    }

}
