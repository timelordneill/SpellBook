package com.example.spellbook.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import com.example.spellbook.domain.Spell
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(detail_frame != null){
            twoPane=true
        }
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction()
            .add(R.id.list_frame, SpellListFragment())
            .addToBackStack("main")
            .commit()

    }
}
