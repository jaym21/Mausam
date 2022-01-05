package dev.jaym21.mausam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.jaym21.mausam.R
import dev.jaym21.mausam.data.remote.models.entities.Hourly
import dev.jaym21.mausam.utils.DateUtils

class HourlyForecastAdapter: ListAdapter<Hourly, HourlyForecastAdapter.HourlyForecastViewHolder>(HourlyForecastDiffUtil()) {

    class HourlyForecastDiffUtil: DiffUtil.ItemCallback<Hourly>() {

        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    inner class HourlyForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val hour: TextView = itemView.findViewById(R.id.tvHour)
        val icon: ImageView = itemView.findViewById(R.id.ivIconHourly)
        val temperature: TextView = itemView.findViewById(R.id.tvTempHourly)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        return HourlyForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hourly_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val currentHour = getItem(position)
        val dateConverted = DateUtils.convertUnixToDate(currentHour.dt.toString())
        val dateOnly = dateConverted.subSequence(0, 5)
        val timeOnly = dateConverted.subSequence(11, 19)
        holder.date.text = dateOnly
        holder.hour.text = timeOnly
        holder.temperature.text = currentHour.temp.toString() + "\u2103"

        when (currentHour.weather?.get(0)?.main?.lowercase()) {
            "clear" -> Glide.with(holder.itemView.context).load(R.drawable.ic_sun).into(holder.icon)
            "rain" -> Glide.with(holder.itemView.context).load(R.drawable.ic_rain).into(holder.icon)
            "drizzle" -> Glide.with(holder.itemView.context).load(R.drawable.ic_rain).into(holder.icon)
            "thunderstorm" -> Glide.with(holder.itemView.context).load(R.drawable.ic_storm).into(holder.icon)
            "snow" -> Glide.with(holder.itemView.context).load(R.drawable.ic_snowfall).into(holder.icon)
            "clouds" -> Glide.with(holder.itemView.context).load(R.drawable.ic_clouds).into(holder.icon)
            "fog" -> Glide.with(holder.itemView.context).load(R.drawable.ic_foggy_day).into(holder.icon)
            "mist" -> Glide.with(holder.itemView.context).load(R.drawable.ic_mist).into(holder.icon)
            "smoke" -> Glide.with(holder.itemView.context).load(R.drawable.ic_mist).into(holder.icon)
            "dust" -> Glide.with(holder.itemView.context).load(R.drawable.ic_mist).into(holder.icon)
            "tornado" -> Glide.with(holder.itemView.context).load(R.drawable.ic_tornado).into(holder.icon)
            "haze" -> Glide.with(holder.itemView.context).load(R.drawable.ic_mist).into(holder.icon)
        }
    }
}