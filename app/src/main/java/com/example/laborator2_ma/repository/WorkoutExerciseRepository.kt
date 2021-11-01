package com.example.laborator2_ma.repository

import com.example.laborator2_ma.domain.WorkoutExercise

interface WorkoutExerciseRepository {

    fun findAll(): List<WorkoutExercise>

    fun remove(position: Int)

    fun add(workoutExercise: WorkoutExercise): Int

    fun modify(workoutExercise: WorkoutExercise, position: Int)
}