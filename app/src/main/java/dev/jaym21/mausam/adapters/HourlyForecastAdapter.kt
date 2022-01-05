package dev.jaym21.mausam.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.jaym21.mausam.data.remote.models.responses.HourlyForecastResponse

class HourlyForecastAdapter: ListAdapter<HourlyForecastResponse, HourlyForecastAdapter.HourlyForecastViewHolder>(HourlyForecastDiffUtil()) {

    class HourlyForecastDiffUtil: DiffUtil.ItemCallback<HourlyForecastResponse>() {
        override fun areItemsTheSame(
            oldItem: HourlyForecastResponse,
            newItem: HourlyForecastResponse
        ): Boolean {
            return oldItem.lat == newItem.lat && oldItem.lon == newItem.lon
        }

        override fun areContentsTheSame(
            oldItem: HourlyForecastResponse,
            newItem: HourlyForecastResponse
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class HourlyForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {

    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {

    }
}