package com.tyas.smartfarm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tyas.smartfarm.databinding.ActivityMainBinding
import com.tyas.smartfarm.view.pages.fragment.PlantFragment
import com.tyas.smartfarm.view.pages.fragment.ProfileFragment
import com.tyas.smartfarm.view.pages.fragment.WeatherFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(WeatherFragment())

        binding.bottomNavigation.setOnItemSelectedListener { id ->
            val fragment: Fragment = when (id) {
                R.id.navigation_lay_weather -> WeatherFragment()
                R.id.navigation_lay_plant -> PlantFragment()
                R.id.navigation_lay_profile -> ProfileFragment()
                else -> WeatherFragment()
            }
            loadFragment(fragment)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
