package com.example.leagueofheroes.data

import com.google.gson.annotations.SerializedName

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
        val biography: Biography,
        val work: Work,
        val appearance: Appearance,
        val image: Image,
    )


// en esta clase creamos el modelo de datos de la biografía del super heroe
class Biography (
    val publisher: String,
    @SerializedName("full-name") val realName: String,
    @SerializedName("place-of-birth") val placeOfBirth: String,
    val alignment: String
)
class Work (
    val occupation: String,
    val base: String
)

class Appearance (
    val gender: String,
    val race: String,
    @SerializedName("eye-color") val eyeColor: String,
    @SerializedName("hair-color") val hairColor: String,
    val height: List<String>,
    val weight: List<String>,
) {
    fun getWeightKg(): String {
        return weight[1]
    }

    fun getHeightCm(): String {
        return height[1]
    }
}
// en esta clase creamos el modelo de datos de la imagen osea la URL de la imagen
class Image (val url: String)
