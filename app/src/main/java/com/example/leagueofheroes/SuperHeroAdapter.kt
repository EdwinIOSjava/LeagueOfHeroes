package com.example.leagueofheroes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.squareup.picasso.Picasso

// en esta clase creamos el adaptador para el RV
class SuperHeroAdapter (var items : List<SuperHero>): Adapter<SuperHeroViewHolder>() {
    // creamos los 3 metodos para el RV
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroViewHolder {
        // el item_superhero es el XML de cada item del RV
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperHeroViewHolder(view)

    }
//items sera cada heroe individual
    override fun getItemCount(): Int {
       return  items.size
    }

    override fun onBindViewHolder(holder: SuperHeroViewHolder, position: Int) {

    }


}
class SuperHeroViewHolder (view: View): RecyclerView.ViewHolder(view) {
    val nameTextView: TextView = view.findViewById(R.id.nameHero_textView)
    val pictureImageView: ImageView = view.findViewById(R.id.hero_imageView)

    fun render(superhero: SuperHero) {
        nameTextView.text = superhero.name
        Picasso.get().load(superhero.image.url).into(pictureImageView);
    }
}