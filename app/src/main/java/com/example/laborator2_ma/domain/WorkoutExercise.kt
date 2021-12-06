package com.example.laborator2_ma.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercise")
data class WorkoutExercise(
     @PrimaryKey(autoGenerate = true) var workoutExerciseId: Int,
     @ColumnInfo(name = "name") var name: String,
     @ColumnInfo(name = "number_reps") var numberOfReps: Int,
     @ColumnInfo(name = "number_sets") var numberOfSets: Int,
     @ColumnInfo(name = "weight") var weight: Float,
     @ColumnInfo(name = "exercise_type") var exerciseType: WorkoutExerciseType,
     @ColumnInfo(name = "workout_set_id") var workoutSetId: Int,
)