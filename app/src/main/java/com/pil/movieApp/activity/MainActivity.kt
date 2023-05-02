package com.pil.movieApp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.visibility = View.INVISIBLE

        /*val toolbar = binding.root.findViewById<View>(R.id.toolbar)
        toolbar.visibility = View.GONE*/

        // binding.toolbar.root.visibility = View.GONE

        binding.btnMovies.setOnClickListener {
            val intent = Intent(this, MovieActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
