package com.example.leagueofheroes.data
// en esta clase creamos el modelo de datos de la respuesta de la API
class SuperHeroResponse (
    val response: String,
    val results: List<SuperHero>
    ) {

    }
// en esta clase creamos el modelo de datos de un super heroe
    class SuperHero (
        val id: String,
        val name: String,
        val image: Image
    ) {

    }
// en esta clase creamos el modelo de datos de la imagen osea la URL de la imagen
    class Image (val url: String)
