package com.example.spellbook.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.ui.SpellViewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_spell_list.*
import java.lang.Class


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SpellListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class SpellListFragment : Fragment() {

    private lateinit var viewModel: SpellViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spell_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        viewModel.getSpells().observe(this, Observer {
            spell_list.adapter = SimpleItemRecyclerViewAdapter(this, it!!)
        })

        spell_list.layoutManager= LinearLayoutManager(activity)
    }

    fun showDetail(spell: Spell){
        val detailFragment = SpellDetailFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, detailFragment)
            .addToBackStack(null)
            .commit()
        detailFragment.addObject(spell)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.listfragmentmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
