package com.example.laborator2_ma.domain

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutSetWithExercises(
    @Embedded val workoutSet: WorkoutSet,
    @Relation(
        parentColumn = "workoutSetId",
        entityColumn = "workoutExerciseId"
    )
    val workoutExercises: List<WorkoutExercise>,
)