package com.example.spellbook.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import com.example.spellbook.Fragments.SavedSpellbooksFragment
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import com.example.spellbook.utils.twoPane
import kotlinx.android.synthetic.main.activity_main.*

/**
 * [MainActivity] is the activity in which all the fragments are loaded.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Sets the global variable [twoPane] to true on tablets.
     * Sets the correct toolbar and configures the menu button for the nav drawer and fills the drawer
     * Sets the list as fragment when app is first started
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        if(detail_frame != null){
            twoPane=true
        }

        //set toolbar
        setSupportActionBar(findViewById(R.id.my_toolbar))
        //set menu icon and drawer
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)
        }

        //set list of spells as main fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.list_frame, SpellListFragment())
            .commit()

        //set list of saved spellbooks as fragment in drawer
        supportFragmentManager.beginTransaction()
            .add(R.id.list_frame_spellbooks, SavedSpellbooksFragment())
            .commit()
    }

    /**
     * Sets the toolbar title to fals
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        return true
    }

    /**
     * Expands the nav drawer when menu button is clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
