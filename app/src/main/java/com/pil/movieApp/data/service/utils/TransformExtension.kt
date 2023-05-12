package com.pil.movieApp.data.service.utils


import com.pil.movieApp.data.entity.MovieEntity
import com.pil.movieApp.data.service.response.MovieResponse
import com.pil.movieApp.domain.entity.Movie

fun MovieResponse.transformToList(): List<Movie> {
    val movieList = mutableListOf<Movie>()
    movieList.forEach() {
        movieList.add(
            Movie(
                it.title,
                it.overview,
                it.posterPath,
                it.releaseDate,
                it.originalLanguage,
                it.voteAverage,
                it.voteCount
            )
        )
    }
    return movieList
}

fun MovieResponse.toMovie() = Movie(
    this.title, this.overview, this.posterPath, this.releaseDate,this.originalLanguage,this.voteAverage,this.voteCount
)

fun MovieEntity.toCharacter() = Movie(this.title, this.overview, this.posterPath, this.releaseDate,this.originalLanguage,this.voteAverage,this.voteCount)

fun Movie.toMovieDB() = MovieEntity(this.title, this.overview, this.posterPath, this.releaseDate,this.originalLanguage,this.voteAverage,this.voteCount)

fun List<MovieEntity>.toMovieList() = this.map { it.toCharacter() }