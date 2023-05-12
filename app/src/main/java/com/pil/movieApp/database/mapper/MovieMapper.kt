package com.pil.movieApp.database.mapper

import com.pil.movieApp.data.entity.MovieEntity
import com.pil.movieApp.data.service.response.MovieResponse

fun MovieResponse.mapToDataBaseExercise(): MovieEntity =
    MovieEntity(
        title = title,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        originalLanguage = originalLanguage,
        voteAverage = voteAverage,
        voteCount = voteCount
    )

fun List<MovieEntity>.mapToLocalExercise(): List<MovieResponse> =
    map { entity ->
        MovieResponse(
            title = entity.title,
            overview = entity.overview,
            posterPath = entity.posterPath,
            releaseDate = entity.releaseDate,
            originalLanguage = entity.originalLanguage,
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount
        )
    }
