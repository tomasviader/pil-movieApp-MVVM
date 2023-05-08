package com.pil.movieApp.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pil.movieApp.util.AlertErrorDialog
import com.pil.retrofit_room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.visibility = View.INVISIBLE
        val intentMovieActivity = Intent(this, MovieActivity::class.java)
        val intentMainActivity = Intent(this, MainActivity::class.java)

        binding.btnMovies.setOnClickListener {
            startActivity(intentMovieActivity)
            finish()
        }

        binding.errorDialog.setOnClickListener{
            AlertErrorDialog.showDialogError(this)
        }

    }

}
