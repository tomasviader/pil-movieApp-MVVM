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
import com.pil.movieApp.data.database.MovieDB
import com.pil.movieApp.data.database.MovieDataBaseImpl
import com.pil.movieApp.data.service.MovieRequestGenerator
import com.pil.movieApp.data.service.MovieServiceImpl
import com.pil.movieApp.data.service.api.MovieClient
import com.pil.movieApp.di.*
import com.pil.movieApp.presentation.adapter.MovieAdapter
import com.pil.movieApp.presentation.mvvm.viewmodel.MoviesViewModel
import com.pil.movieApp.domain.util.ErrorDialogFragment
import com.pil.movieApp.presentation.di.ModelModule
import com.pil.movieApp.presentation.di.ViewModelModule
import com.pil.retrofit_room.R
import com.pil.retrofit_room.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin


class MovieActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MoviesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startKoin {
            androidContext(this@MovieActivity)

            modules(
                listOf(
                    ViewModelModule.viewModelModule,
                    ServiceModule.serviceModule,
                    ModelModule.modelModule,
                    UseCaseModule.useCaseModule,
                    ApiModule.apiModule,
                    DBModule.dbModule,
                    DataBaseModule.dataBaseModule
                )
            )
        }

        val intentMainActivity = Intent(this, MainActivity::class.java)

        with(binding.btnBack){
            visibility = VISIBLE
            setOnClickListener {
                startActivity(intentMainActivity)
                finish()
            }
        }

       /* val dataBase: MovieDB by lazy {
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
        )[MoviesViewModel::class.java]*/

        viewModel.callService()
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