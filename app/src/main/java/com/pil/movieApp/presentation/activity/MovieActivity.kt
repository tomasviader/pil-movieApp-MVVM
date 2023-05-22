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

        val intentMainActivity = Intent(this, MainActivity::class.java)

        with(binding.btnBack) {
            visibility = VISIBLE
            setOnClickListener {
                startActivity(intentMainActivity)
                finish()
            }
        }

        viewModel.callService()
        viewModel.getValue().observe(this) { updateUI(it) }
    }


    private fun updateUI(data: MoviesViewModel.MainData) {
        when (data.status) {
            MoviesViewModel.MainStatus.SHOW_INFO -> {
                binding.recycler.layoutManager = LinearLayoutManager(this)
                binding.recycler.adapter = MovieAdapter(data.movies)
            }

            MoviesViewModel.MainStatus.EMPTY_STATE -> {
                binding.emptyStateText.visibility = VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callService()

        with(binding) {
            title.visibility = GONE
            btnMovies.visibility = GONE
            appDescription.visibility = GONE
            errorDialog.visibility = GONE
        }
    }
}