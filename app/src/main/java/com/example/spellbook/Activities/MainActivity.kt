package com.example.spellbook.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if(detail_frame != null){
            twoPane=true
        }

        //set toolbar
        setSupportActionBar(findViewById(R.id.my_toolbar))

        supportFragmentManager.beginTransaction()
            .add(R.id.list_frame, SpellListFragment())
            .addToBackStack("main")
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        menuInflater.inflate(R.menu.listfragmentmenu,menu)

        return true
    }
}
