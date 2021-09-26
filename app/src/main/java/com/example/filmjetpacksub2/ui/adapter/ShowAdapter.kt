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
import com.example.filmjetpacksub2.data.source.local.entity.ShowEntity
import com.example.filmjetpacksub2.databinding.HomeItemBinding
import com.example.filmjetpacksub2.ui.detail.DetailActivity
import com.example.filmjetpacksub2.utils.MappingHelper
import java.text.SimpleDateFormat
import java.util.*

class ShowAdapter: PagedListAdapter<ShowEntity, ShowAdapter.ShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private const val SHOWS_TYPE = "TV SHOWS"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ShowEntity>() {
            override fun areItemsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ShowEntity, newItem: ShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val binding = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = getItem(position)
        if(show != null) holder.bind(show)
    }

    class ShowViewHolder(private val binding: HomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ShowEntity) {
            with(binding) {
                tvJudul.text = film.title
                val dateFormatBefore = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateFormatAfter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                val dateForm = dateFormatBefore.parse(film.firstAirDate)
                val dateText = dateFormatAfter.format(dateForm)
                tvDate.text = dateText

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_FRAGMENT, SHOWS_TYPE)
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