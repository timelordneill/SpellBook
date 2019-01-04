package com.example.spellbook.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.Spell
import kotlinx.android.synthetic.main.fragment_spell_detail.*
import kotlinx.android.synthetic.main.fragment_spell_detail.view.*

class SpellDetailFragment : android.support.v4.app.Fragment() {

    private lateinit var spell: Spell
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onStart() {
        super.onStart()
        add_button.setOnClickListener {
            val addSpellFragment = AddSpellFragment()
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.list_frame, addSpellFragment)
                .addToBackStack(null)
                .commit()
            addSpellFragment.addObject(spell)
        }
    }

    fun addObject(item: Spell){
        this.spell = item
    }

}
