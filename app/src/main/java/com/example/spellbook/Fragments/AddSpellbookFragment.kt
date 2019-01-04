package com.example.spellbook.Fragments

import android.app.ActionBar
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Spinner

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.fragment_add_spellbook.*
import kotlinx.android.synthetic.main.fragment_spell_list.*
import kotlinx.android.synthetic.main.popup_add_class.view.*


class AddSpellbookFragment : Fragment() {

    private lateinit var spellbook:Spellbook
    private var classes= mutableListOf<CharacterClass>()
    private lateinit var spellbookViewmodel:SpellbookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_spellbook, container, false)
    }

    override fun onStart() {
        super.onStart()

        class_recyclerview.adapter = ClassRecyclerViewAdapter(this, classes)
        class_recyclerview.layoutManager= LinearLayoutManager(activity)

        //set popup to open when button is clicked
        val view=layoutInflater.inflate(R.layout.popup_add_class, null)
        var popup=PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)

        view.class_spinner.adapter=ArrayAdapter<Classes>(context, android.R.layout.simple_spinner_item, Classes.values())

        view.level_np.maxValue=20
        view.level_np.minValue=1

        view.add_class_button.setOnClickListener {
            val characterClass= view.class_spinner.selectedItem as Classes
            classes.add(CharacterClass(characterClass, view.level_np.value))
            class_recyclerview.adapter = ClassRecyclerViewAdapter(this, classes)
            class_recyclerview.layoutManager= LinearLayoutManager(activity)
            popup.dismiss()
        }

        add_class_button.setOnClickListener {
            popup.showAsDropDown(spellbook_constraint, 10, 10)
        }

        create_spellbook_button.setOnClickListener {
            val name=character_name_text.text.toString()
            val book=Spellbook(name, classes, arrayListOf())
            val converter=DatabaseSpellbookConverter()
            spellbookViewmodel.insert(converter.toDatabaseSpellbook(book))
            val listFragment = SpellListFragment()
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.list_frame, listFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
