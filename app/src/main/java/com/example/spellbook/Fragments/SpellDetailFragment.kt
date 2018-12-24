package com.example.spellbook.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.Spell
import kotlinx.android.synthetic.main.fragment_spell_detail.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SpellDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SpellDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
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

    fun addObject(item: Spell){
        this.spell = item
    }

}
