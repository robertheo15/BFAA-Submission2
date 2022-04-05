package com.example.aplikasigithubusernavigationdanapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.aplikasigithubusernavigationdanapi.databinding.ActivityMainBinding
import com.example.aplikasigithubusernavigationdanapi.view.HomeFragment
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment())
                    .addToBackStack(null)
                    .commit()
                true
            }
            R.id.exit -> {
                finish()
                exitProcess(0)
            }
            else -> true
        }
    }
}