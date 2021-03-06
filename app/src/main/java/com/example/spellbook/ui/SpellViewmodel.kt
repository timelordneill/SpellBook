package com.example.spellbook.ui

import android.arch.lifecycle.MutableLiveData
import com.example.spellbook.base.InjectedViewModel
import com.example.spellbook.domain.Spell
import com.example.spellbook.network.SpellAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Viewmodel that gets [Spell] from RestAPI
 */
class SpellViewmodel : InjectedViewModel(){

    private val spells = MutableLiveData<List<Spell>>()

    @Inject
    lateinit var spellApi: SpellAPI

    /**
     * Indicates whether the loading view should be displayed.
     */
    val loadingVisibility: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * Represents a disposable resources
     */
    private var subscription: Disposable

    init {
        subscription = spellApi.getAllSpells()
            //we tell it to fetch the data on background by
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveSpellStart() }
            .doOnTerminate { onRetrieveSpellFinish() }
            .subscribe(
                { result -> onRetrieveSpellSuccess(result) },
                { error -> onRetrieveSpellError(error) }
            )
    }

    private fun onRetrieveSpellError(error: Throwable) {
        //Currently requests fail silently, which isn't great for the user.
        //It would be better to show a Toast, or maybe make a TextView visible with the error message.
        Logger.e(error.message!!)
    }

    private fun onRetrieveSpellSuccess(result: List<Spell>) {
        spells.value=result
    }

    private fun onRetrieveSpellFinish() {
        Logger.i("Finished retrieving info")
        loadingVisibility.value = false
    }

    private fun onRetrieveSpellStart() {
        Logger.i("Started retrieving info")
        loadingVisibility.value = true
    }

    fun getSpells(): MutableLiveData<List<Spell>> {
        Logger.i(spells.toString())
        return spells
    }

}