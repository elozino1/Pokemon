package com.example.pokemon.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokemon.R
import com.example.pokemon.models.PokemonResult

class PokemonListAdapter(
    private val listener: IOnItemClickListener
) : RecyclerView.Adapter<PokemonListAdapter.PokemonListViewHolder>() {

    inner class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var pokemonName: TextView = itemView.findViewById(R.id.pokemon_list_text)
        var pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_list_image)
        var pokemonNumber: TextView = itemView.findViewById(R.id.pokemon_list_number)
        var imageCardView: CardView = itemView.findViewById(R.id.image_card_view)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    private val differCallBack = object: DiffUtil.ItemCallback<PokemonResult>() {
        override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        //get reference to the textview and set the pokemon name
        val pokemonNameText = holder.pokemonName
        pokemonNameText.text = differ.currentList[position].name

        val imageId = position + 1

        val pokemonNumberText = holder.pokemonNumber
        val pokemonIndex = "#$imageId"
        pokemonNumberText.text = pokemonIndex

        val pokemonImageHolder = holder.pokemonImage

        val imageCardView = holder.imageCardView

        //load pokemon into imageView and apply colour to cardView from pokemon image
        Glide.with(holder.itemView)
            .asBitmap()
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${imageId}.png")
            .listener(object: RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ) = false

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    if (resource != null) {
                        Palette.from(resource).maximumColorCount(8).generate { palette ->
                            val darkVibrantSwatch = palette!!.darkVibrantSwatch
                            val dominantSwatch = palette.dominantSwatch
                            val lightVibrantSwatch = palette.lightVibrantSwatch
                            when {
                                darkVibrantSwatch != null -> {
                                    imageCardView.setCardBackgroundColor(darkVibrantSwatch.rgb)
                                }
                                dominantSwatch != null -> {
                                    imageCardView.setCardBackgroundColor(dominantSwatch.rgb)
                                }
                                else -> {
                                    imageCardView.setCardBackgroundColor(lightVibrantSwatch!!.rgb)
                                }
                            }
                            val vibrant = palette.dominantSwatch
                            if(vibrant != null) {
                                pokemonNameText.setTextColor(vibrant.bodyTextColor)
                                pokemonNumberText.setTextColor(vibrant.bodyTextColor)
                            }
                        }
                    }
                    return false
                }

            })
            .into(pokemonImageHolder)
    }

    override fun getItemCount() = differ.currentList.size
}