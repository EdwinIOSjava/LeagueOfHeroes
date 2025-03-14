package com.example.leagueofheroes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    //creo las variables para poder acceder a ellas desde cualquier parte del codigo
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            // le doys valores a las variables accediento por el ID a sus respectivos componentes
            initComponents()
          //  initListeners()


        }
    }



    private fun initComponents() {
        //variable=find..<[tipoDeComponente]>(direccion donde se encuentra el  comoponente ej: R.id. XXXX
        recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
    }
    private fun initListeners() {

    }
}