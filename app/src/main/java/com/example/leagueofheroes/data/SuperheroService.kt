package com.example.leagueofheroes.data

import retrofit2.http.GET
import retrofit2.http.Path

interface SuperHeroService {
    @GET("search/{name}")
    suspend fun findSuperheroesByName(@Path("name") query: String): SuperHeroResponse

    @GET("{superhero-id}")
    suspend fun findSuperheroById(@Path("superhero-id") id: String): SuperHero
}