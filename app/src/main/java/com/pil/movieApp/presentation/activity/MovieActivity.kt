package com.pil.movieApp.presentation.activity


import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pil.movieApp.presentation.adapter.MovieAdapter
import com.pil.movieApp.presentation.mvvm.viewmodel.MoviesViewModel
import com.pil.retrofit_room.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent


class MovieActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MoviesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val intentMainActivity = Intent(this, MainActivity::class.java)

        with(binding.btnBack) {
            visibility = VISIBLE
            setOnClickListener {
                startActivity(intentMainActivity)
                finish()
            }
        }*/

        binding.btnBack.setOnClickListener { viewModel.onBackButtonPressed() }

        viewModel.getValue().observe(this) { updateUI(it) }
        viewModel.callService()
    }


    private fun updateUI(data: MoviesViewModel.MoviesData) {
        when (data.status) {
            MoviesViewModel.MoviesStatus.SHOW_INFO -> {
                binding.recycler.layoutManager = LinearLayoutManager(this)
                binding.recycler.adapter = MovieAdapter(data.movies)
            }
            MoviesViewModel.MoviesStatus.EMPTY_STATE -> {
                binding.emptyStateText.visibility = VISIBLE
            }
            MoviesViewModel.MoviesStatus.BACK_BUTTON -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            btnBack.visibility = VISIBLE
            title.visibility = GONE
            btnMovies.visibility = GONE
            appDescription.visibility = GONE
            errorDialog.visibility = GONE
        }
    }
}