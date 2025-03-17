package com.example.leagueofheroes.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leagueofheroes.R
import com.example.leagueofheroes.adapters.SuperHeroAdapter
import com.example.leagueofheroes.data.SuperHero
import com.example.leagueofheroes.data.SuperheroService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    //creo las variables para poder acceder a ellas desde cualquier parte del codigo
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SuperHeroAdapter
    // creamos una lista de superheroes vacia para poder llenarla con los resultados de la API
    var superheroList: List<SuperHero> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // le doy valores a las variables accediento por el ID a sus respectivos componentes
        initComponents()
        //  initListeners()

        recyclerView = findViewById(R.id.recyclerView)

        adapter = SuperHeroAdapter(superheroList) { position ->
            val superhero = superheroList[position]

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("SUPERHERO_ID", superhero.id)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        searchSuperheroesByName("b")


    }
    // iconos y botones de la barra de arriba (menu)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchSuperheroesByName(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        return true
    }


    private fun initComponents() {
        //variable=find..<[tipoDeComponente]>(direccion donde se encuentra el  comoponente ej: R.id. XXXX
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
    }

    // usamos retrofit para hacer la peticion a la API
    fun getRetrofit(): SuperheroService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.superheroapi.com/api.php/7252591128153666/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SuperheroService::class.java)
    }


    private fun initListeners() {

    }
    // usamos corrutinas para hacer la peticion a la API y mostrar los resultados en el RV
    fun searchSuperheroesByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = getRetrofit()
                val result = service.findSuperheroesByName(query)

                superheroList = result.results

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = superheroList
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}