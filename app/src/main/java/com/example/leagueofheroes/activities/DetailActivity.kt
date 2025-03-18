package com.example.leagueofheroes.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.leagueofheroes.R
import com.example.leagueofheroes.data.SuperHero
import com.example.leagueofheroes.data.SuperHeroService
import com.example.leagueofheroes.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding
    lateinit var superhero: SuperHero

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // inicializamos el binding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val id = intent.getStringExtra("SUPERHERO_ID")!!
        getSuperheroById(id)

        // tenemos que capturar el click en el boton de la barra de menus

        binding.navigationBarDatail.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_biography -> {
                    binding.appearanceContentDetail.root.visibility = View.GONE
                    binding.statsContentDetail.root.visibility = View.GONE
                    binding.biographyContentDetail.root.visibility = View.VISIBLE
                }
                R.id.action_appearance -> {
                    binding.statsContentDetail.root.visibility = View.GONE
                    binding.biographyContentDetail.root.visibility = View.GONE
                    binding.appearanceContentDetail.root.visibility = View.VISIBLE
                }
                R.id.action_stats -> {
                    binding.biographyContentDetail.root.visibility = View.GONE
                    binding.appearanceContentDetail.root.visibility = View.GONE
                    binding.statsContentDetail.root.visibility = View.VISIBLE
                }
            }
            true
        }

        binding.navigationBarDatail.selectedItemId = R.id.action_biography
    }


    fun loadData() {
        Picasso.get().load(superhero.image.url).into(binding.heroImageView)
// Tenemos que rellenar la información del superheroe en pantalla
        supportActionBar?.title = superhero.name
        supportActionBar?.subtitle = superhero.biography.realName

        // Biography
        binding.biographyContentDetail.publisherTextView.text = superhero.biography.publisher
        binding.biographyContentDetail.placeOfBirthTextView.text = superhero.biography.placeOfBirth
        binding.biographyContentDetail.alignmentTextView.text = superhero.biography.alignment
        binding.biographyContentDetail.occupationTextView.text = superhero.work.occupation
        binding.biographyContentDetail.baseTextView.text = superhero.work.base

        // Appearance
        binding.appearanceContentDetail.raceTextView.text = superhero.appearance.race
        binding.appearanceContentDetail.genderTextView.text = superhero.appearance.gender
        binding.appearanceContentDetail.eyeColorTextView.text = superhero.appearance.eyeColor
        binding.appearanceContentDetail.hairColorTextView.text = superhero.appearance.hairColor
        binding.appearanceContentDetail.weightTextView.text = superhero.appearance.getWeightKg()
        binding.appearanceContentDetail.heightTextView.text = superhero.appearance.getHeightCm()

        // Stats
        binding.statsContentDetail.strengthProgressBar.progress=superhero.powerstats.strength.toInt()
        binding.statsContentDetail.intelligenceProgressBar.progress=superhero.powerstats.intelligence.toInt()
        binding.statsContentDetail.speedProgressBar.progress=superhero.powerstats.speed.toInt()
        binding.statsContentDetail.durabilityProgressBar.progress=superhero.powerstats.durability.toInt()
        binding.statsContentDetail.powerProgressBar.progress=superhero.powerstats.power.toInt()


    }




    fun getRetrofit(): SuperHeroService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SuperHeroService::class.java)
    }

    fun getSuperheroById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                superhero = service.findSuperheroById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    loadData()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}