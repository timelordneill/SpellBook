package com.example.spellbook.network

import com.example.spellbook.domain.Spell
import io.reactivex.Observable
import retrofit2.http.GET

interface SpellAPI {

    @GET("/spell/")
    fun getAllSpells(): Observable<List<Spell>>
}