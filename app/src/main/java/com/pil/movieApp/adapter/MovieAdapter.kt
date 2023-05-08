package com.pil.movieApp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.pil.movieApp.service.model.Movie
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ItemRecyclerBinding

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRecyclerBinding.bind(itemView)

        fun bind(movie: Movie) {
            binding.movieTitle.text = itemView.context.getString(R.string.card_title, movie.title)
            binding.overview.text = itemView.context.getString(R.string.card_overview, movie.overview)
            binding.releaseDate.text = itemView.context.getString(R.string.card_release_date, movie.releaseDate)
            binding.voteCount.text = itemView.context.getString(R.string.card_vote_count, movie.voteCount.toString())
            binding.voteAverage.text = itemView.context.getString(R.string.card_vote_average, movie.voteAverage.toString())
            binding.originalLanguage.text = itemView.context.getString(R.string.card_original_language, movie.originalLanguage)

            Glide.with(itemView.context)
                .load( urlImage + movie.posterPath)
                .into(binding.image)
        }
    }
    companion object{
        private const val urlImage = "https://image.tmdb.org/t/p/w500"
    }
}
