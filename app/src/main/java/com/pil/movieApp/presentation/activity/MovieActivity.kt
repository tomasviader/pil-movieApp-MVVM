package com.pil.movieApp.presentation.activity


import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.pil.movieApp.presentation.adapter.MovieAdapter
import com.pil.movieApp.database.MovieDataBaseImpl
import com.pil.movieApp.data.database.MovieDB
import com.pil.movieApp.presentation.mvvm.contract.MainContract
import com.pil.movieApp.domain.usecase.MainModel
import com.pil.movieApp.presentation.mvvm.viewmodel.MoviesViewModel
import com.pil.movieApp.presentation.mvvm.viewmodel.factory.ViewModelFactory
import com.pil.movieApp.data.service.api.MovieClient
import com.pil.movieApp.data.service.MovieRequestGenerator
import com.pil.movieApp.data.service.MovieServiceImpl
import com.pil.movieApp.domain.util.ErrorDialogFragment
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding


class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainContract.ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentMainActivity = Intent(this, MainActivity::class.java)

        with(binding.btnBack){
            visibility = VISIBLE
            setOnClickListener {
                startActivity(intentMainActivity)
                finish()
            }
        }

        val dataBase: MovieDB by lazy {
            Room
                .databaseBuilder(this, MovieDB::class.java, "Movie-DataBase")
                .build()
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                arrayOf(
                    MainModel(
                        MovieServiceImpl(MovieRequestGenerator.createService(MovieClient::class.java)),
                        MovieDataBaseImpl(dataBase.moviesDao()),
                    ),
                ),
            ),
        )[MoviesViewModel::class.java]

        viewModel.getValue().observe(this) { updateUI(it) }
    }


    private fun updateUI(data: MoviesViewModel.MainData) {
        when (data.status) {
            MoviesViewModel.MainStatus.SHOW_INFO -> {
                if (data.movies.isEmpty()){
                    binding.emptyStateText.visibility = RecyclerView.VISIBLE
                }
                else{
                    binding.recycler.layoutManager = LinearLayoutManager(this)
                    binding.recycler.adapter =  MovieAdapter(data.movies)
                }
            }
            MoviesViewModel.MainStatus.ERROR -> {
                ErrorDialogFragment.newInstance(getString(R.string.alert_title),
                    getString(R.string.alert_message)).show(supportFragmentManager,getString(R.string.fragment_tag))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.callService()

        with(binding){
            title.visibility = GONE
            btnMovies.visibility = GONE
            appDescription.visibility = GONE
            errorDialog.visibility= GONE
        }
    }
}