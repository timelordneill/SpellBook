package com.example.spellbook.Fragments

import android.app.ActionBar
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Toast

import com.example.spellbook.R
import com.example.spellbook.domain.CharacterClass
import com.example.spellbook.domain.Classes
import com.example.spellbook.domain.DatabaseSpellbookConverter
import com.example.spellbook.domain.RecyclerViewAdapters.EditSpellbookRecyclerViewAdapter
import com.example.spellbook.domain.Spellbook
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.fragment_spellbook_edit.*
import kotlinx.android.synthetic.main.popup_add_class.view.*

/**
 * [SpellbookEditFragment] edits the name and characterclasses of a [Spellbook]
 */
class SpellbookEditFragment : android.support.v4.app.Fragment() {

    private lateinit var spellbook:Spellbook
    private lateinit var  spellbookViewmodel: SpellbookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spellbook_edit, container, false)
    }

    override fun onStart() {
        super.onStart()

        class_recyclerview.adapter = EditSpellbookRecyclerViewAdapter(this, spellbook.characterClass)
        class_recyclerview.layoutManager= LinearLayoutManager(activity)

        character_name_text.setText(spellbook.name)

        //set popup to open when button is clicked
        val view=layoutInflater.inflate(R.layout.popup_add_class, null)
        val popup= PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)

        view.class_spinner.adapter= ArrayAdapter<Classes>(context, android.R.layout.simple_spinner_item, Classes.values())

        view.level_np.maxValue=20
        view.level_np.minValue=1

        view.add_class_button.setOnClickListener {
            var totalLevel=0
            spellbook.characterClass.forEach { characterClass ->
                totalLevel += characterClass.level
            }

            if(totalLevel + view.level_np.value > 20){
                val toast = Toast.makeText(context, "Character level can not exeed 20", Toast.LENGTH_SHORT)
                toast.show()
                popup.dismiss()
            }else{
                val characterClass= view.class_spinner.selectedItem as Classes
                val sameClass=spellbook.characterClass.filter { characterClass2 ->
                    characterClass == characterClass2.name
                }

                if (sameClass.isEmpty()){
                    spellbook.characterClass.add(CharacterClass(characterClass, view.level_np.value))
                    class_recyclerview.adapter = EditSpellbookRecyclerViewAdapter(this, spellbook.characterClass)
                    class_recyclerview.layoutManager= LinearLayoutManager(activity)
                    popup.dismiss()
                }else{
                    val toast = Toast.makeText(context, "Character can't have the same class twice", Toast.LENGTH_SHORT)
                    toast.show()
                    popup.dismiss()
                }

            }
        }

        add_class_button.setOnClickListener {
            popup.showAsDropDown(spellbook_constraint, 10, 10)
        }

        create_spellbook_button.setOnClickListener {
            if(character_name_text.text.isNullOrEmpty()){
                charactername_textInputLayout.error="Name can not be empty"
            }else if(spellbook.characterClass.isNullOrEmpty()){
                class_error_text.visibility=View.VISIBLE
            }else{
                spellbook.name=character_name_text.text.toString()
                val converter=DatabaseSpellbookConverter(activity)
                spellbookViewmodel.update(converter.toDatabaseSpellbook(spellbook))

                val spellbookFragment = SpellbookFragment()
                this.fragmentManager!!.beginTransaction()
                    .replace(R.id.list_frame, spellbookFragment)
                    .addToBackStack(null)
                    .commit()
                spellbookFragment.addObject(spellbook)
            }
        }
    }

    /**
     * deteles a [CharacterClass] from [spellbook]
     */
    fun deleteClass(characterclass: CharacterClass){
        spellbook.characterClass.remove(characterclass)

        class_recyclerview.adapter = EditSpellbookRecyclerViewAdapter(this, spellbook.characterClass)
        class_recyclerview.layoutManager= LinearLayoutManager(activity)
    }

    /**
     * adds [Spellbook] to the fragment
     */
    fun addObject(spellbook:Spellbook){
        this.spellbook=spellbook
    }
}
