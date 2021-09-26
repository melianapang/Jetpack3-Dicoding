package com.example.filmjetpacksub2.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmjetpacksub2.R
import com.example.filmjetpacksub2.data.source.local.entity.MovieEntity
import com.example.filmjetpacksub2.databinding.HomeItemBinding
import com.example.filmjetpacksub2.ui.detail.DetailActivity
import com.example.filmjetpacksub2.utils.MappingHelper
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter : PagedListAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        private const val MOVIE_TYPE = "MOVIES"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if(movie != null) holder.bind(movie)
    }

    class MovieViewHolder(private val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: MovieEntity) {
            with(binding) {
                tvJudul.text = film.title
                val dateFormatBefore = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateFormatAfter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val dateForm = dateFormatBefore.parse(film.releaseDate)
                val dateText = dateFormatAfter.format(dateForm)
                tvDate.text = dateText

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_FRAGMENT, MOVIE_TYPE)
                    intent.putExtra(DetailActivity.EXTRA_NAME, film.title)
                    intent.putExtra(DetailActivity.EXTRA_FILM_ID, film.id)
                    itemView.context.startActivity(intent)
                }

                val poster = MappingHelper.mapPosterPath(film.posterPath)
                Glide.with(itemView.context)
                    .load(poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(imgPoster)
            }
        }
    }
}