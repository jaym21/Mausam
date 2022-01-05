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
import dev.jaym21.mausam.data.remote.models.entities.Daily
import dev.jaym21.mausam.utils.DateUtils

class DailyForecastAdapter: ListAdapter<Daily, DailyForecastAdapter.DailyForecastViewHolder>(DailyForecastDiffUtil()) {

    class DailyForecastDiffUtil: DiffUtil.ItemCallback<Daily>() {

        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.toString() == newItem.toString()
        }

    }

    inner class DailyForecastViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.tvDay)
        val icon: ImageView = itemView.findViewById(R.id.ivIconDaily)
        val temp: TextView = itemView.findViewById(R.id.tvTempDaily)
        val date: TextView = itemView.findViewById(R.id.tvDateDaily)
        val humidity: TextView = itemView.findViewById(R.id.tvHumidityDaily)
        val sunrise: TextView = itemView.findViewById(R.id.tvSunrise)
        val windSpeed: TextView = itemView.findViewById(R.id.tvWindSpeed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        return DailyForecastViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.daily_rv_item, parent, false))
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val currentDay = getItem(position)

        val day = DateUtils.convertUnixToDay(currentDay.dt.toString())
        val dateConverted = DateUtils.convertUnixToDate(currentDay.dt.toString())
        val dateOnly = dateConverted.subSequence(0, 5)
        holder.day.text = day
        val tempShort = currentDay.temp?.day.toString().subSequence(0 ,4)
        holder.temp.text = "$tempShort" + "\u2103"
        holder.date.text = dateOnly
        holder.humidity.text = currentDay.humidity.toString()
        val sunConverted = DateUtils.convertUnixToDate(currentDay.sunrise.toString())
        val sunriseTime = sunConverted.subSequence(11, 19)
        holder.sunrise.text = sunriseTime
        holder.windSpeed.text = currentDay.windSpeed.toString()

        when (currentDay.weather?.get(0)?.main?.lowercase()) {
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