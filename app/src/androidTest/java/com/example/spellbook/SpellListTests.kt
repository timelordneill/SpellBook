package com.example.spellbook

import android.databinding.adapters.NumberPickerBindingAdapter.setValue
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.example.spellbook.Activities.MainActivity
import com.example.spellbook.domain.RecyclerViewAdapters.SpellRecyclerViewAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.widget.EditText
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.onData
import java.util.regex.Pattern.matches
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.contrib.PickerActions
import android.widget.NumberPicker
import org.hamcrest.CoreMatchers.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View


@RunWith(AndroidJUnit4::class)
class SpellListTests {
    @get:Rule
    val rvActivity = ActivityTestRule<MainActivity>(MainActivity::class.java)

    //test search by searching for the spell "Wish"
    @Test
    fun searchTest(){
        onView(withId(R.id.action_search)).perform(click())
        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("Wish"), pressImeActionButton())
            .perform(ViewActions.closeSoftKeyboard())
        onView(withId(R.id.spell_list)).perform(RecyclerViewActions.scrollToHolder(withHolderPersonView("Wish")))
    }

    @Test
    fun classFilterTest(){
        onView(withId(R.id.class_spinner)).perform(click())
        onView(allOf(withText("Paladin"), isDisplayed())).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        //checks for the spell compelled duel because this is a paladin exclusive spell
        onView(withId(R.id.spell_list)).perform(RecyclerViewActions.scrollToHolder(withHolderPersonView("Compelled Duel")))
    }

    //tests for adding a spellbook do not work because espresso does not recognise elements in popupwindow
/*    @Test
    fun testAddSpellbook(){
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.add_spellbook)).perform(click())
        onView(withId(R.id.character_name_text)).perform(typeText("Kurt"))
        onView(withId(R.id.add_class_button)).perform(click())
        onView(withId(R.id.class_spinner)).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(allOf(withText("Wizard"), isDisplayed())).inRoot(RootMatchers.isPlatformPopup()).perform(click())
        onView(allOf(withText("Add"), isDisplayed())).inRoot(RootMatchers.isPlatformPopup()).perform(click())
    }*/

    fun withHolderPersonView(text: String): Matcher<RecyclerView.ViewHolder> {
        return object : BoundedMatcher<RecyclerView.ViewHolder, SpellRecyclerViewAdapter.ViewHolder>(SpellRecyclerViewAdapter.ViewHolder::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("ViewHolder found with text: $text")
            }

            override fun matchesSafely(item: SpellRecyclerViewAdapter.ViewHolder): Boolean {
                val nameViewText = item.itemView.findViewById<TextView>(R.id.text_name)
                return nameViewText != null && nameViewText.text
                    .toString().contains(text)
            }
        }
    }

    fun setNumber(num: Int): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController, view: View) {
                val np = view as NumberPicker
                np.value = num
            }

            override fun getDescription(): String {
                return "Set the passed number into the NumberPicker"
            }

            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isAssignableFrom(NumberPicker::class.java)
            }
        }
    }
}