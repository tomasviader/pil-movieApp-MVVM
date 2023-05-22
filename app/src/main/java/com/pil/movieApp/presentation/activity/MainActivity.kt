package com.pil.movieApp.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.pil.movieApp.domain.util.ErrorDialogFragment
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMovieActivity = Intent(this, MovieActivity::class.java)
        binding.btnBack.visibility = INVISIBLE

        binding.btnMovies.setOnClickListener {
            startActivity(intentMovieActivity)
            finish()
        }

        binding.errorDialog.setOnClickListener {
            ErrorDialogFragment.newInstance(
                getString(R.string.alert_title),
                getString(R.string.alert_message)
            ).show(supportFragmentManager, getString(R.string.fragment_tag))
        }

    }

}
