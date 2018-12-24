package com.example.spellbook.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_spell_list.*
import java.lang.Class

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SpellListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SpellListFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SpellListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var spells: List<Spell>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spell_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        spells=createspells()

        spell_list.adapter = SimpleItemRecyclerViewAdapter(this, spells!!)
        spell_list.layoutManager=LinearLayoutManager(activity)
    }

    private fun createspells(): List<Spell>{
        var acid= Spell("Acid Splash", 0, "C", "PHB", 211, listOf("ding"), listOf("anderding"),
            listOf(Time(1, "action")), Range("point", Distance("feet", 60)), Components(true, true,"")
            ,Classes(listOf(Class("Sorcerer", "PHB"), Class("Wizard", "PHB")), listOf()),Duration(false,null, null),
            Meta(false),true)

        return listOf(acid)
    }
}