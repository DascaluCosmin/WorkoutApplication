package com.example.laborator2_ma.repository

import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType

class WorkoutExerciseRepositoryInMemory : WorkoutExerciseRepository {

    private var workoutExercises: ArrayList<WorkoutExercise> = ArrayList(
        listOf(
            WorkoutExercise(0,"Squats", 20, 4, 0.0f, WorkoutExerciseType.LEGS),
            WorkoutExercise( 1, "Lunges", 16, 6, 0.0f, WorkoutExerciseType.LEGS),
            WorkoutExercise( 2, "Push-ups", 14, 4, 0.0f, WorkoutExerciseType.CORE),
            WorkoutExercise( 3, "Dumbbell Rows", 10, 5, 5.0f, WorkoutExerciseType.BACK)
        )
    )

    override fun findAll(): List<WorkoutExercise> {
        return workoutExercises
    }

    override fun remove(position: Int) {
        workoutExercises.removeAt(position)
    }

    override fun add(workoutExercise: WorkoutExercise): Int {
        workoutExercises.add(workoutExercise)
        return workoutExercises.size - 1
    }

    override fun modify(workoutExercise: WorkoutExercise, position: Int) {
        workoutExercises[position] = workoutExercise
    }
}