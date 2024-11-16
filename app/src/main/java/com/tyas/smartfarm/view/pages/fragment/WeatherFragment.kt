package com.tyas.smartfarm.view.pages.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tyas.smartfarm.R
import com.tyas.smartfarm.databinding.FragmentWeatherBinding
import com.tyas.smartfarm.view.adapter.DailyForecastAdapter
import com.tyas.smartfarm.view.adapter.DailyWeather
import com.tyas.smartfarm.view.adapter.HourlyForecastAdapter
import com.tyas.smartfarm.view.adapter.HourlyWeather

class WeatherFragment : Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hourlyData = listOf(
            HourlyWeather("12:00", "27°", R.drawable.ic_weather),
            HourlyWeather("13:00", "26°", R.drawable.ic_weather),
            HourlyWeather("14:00", "26°", R.drawable.ic_weather),
            HourlyWeather("15:00", "26°", R.drawable.ic_weather),
            HourlyWeather("16:00", "26°", R.drawable.ic_weather),
            HourlyWeather("17:00", "26°", R.drawable.ic_weather),
            HourlyWeather("18:00", "26°", R.drawable.ic_weather),
            HourlyWeather("19:00", "26°", R.drawable.ic_weather),
            HourlyWeather("20:00", "26°", R.drawable.ic_weather),
            HourlyWeather("21:00", "26°", R.drawable.ic_weather)
        )

        val hourlyAdapter = HourlyForecastAdapter(hourlyData)
        binding.hourlyForecastRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.hourlyForecastRecycler.adapter = hourlyAdapter

        val dailyData = listOf(
            DailyWeather("Hari ini", "Kabut asap", "33° / 25°", R.drawable.ic_weather),
            DailyWeather("Besok", "Badai petir", "33° / 25°", R.drawable.ic_weather),
            DailyWeather("Sabtu", "Badai petir", "34° / 25°", R.drawable.ic_weather),
            DailyWeather("Minggu", "Badai petir", "32° / 25°", R.drawable.ic_weather),
            DailyWeather("Senin", "Badai petir", "32° / 25°", R.drawable.ic_weather),
            DailyWeather("Selasa", "Badai petir", "32° / 25°", R.drawable.ic_weather),
            DailyWeather("Rabu", "Badai petir", "32° / 25°", R.drawable.ic_weather)
        )

        // Setup Daily Forecast RecyclerView
        val dailyAdapter = DailyForecastAdapter(dailyData)
        binding.dailyForecastRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.dailyForecastRecycler.adapter = dailyAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
