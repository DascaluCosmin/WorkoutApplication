package com.example.laborator2_ma.repository

import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.domain.WorkoutSet
import java.time.LocalDate

class WorkoutSetRepositoryInMemory : WorkoutSetRepository {

    private var workoutSets: ArrayList<WorkoutSet> = ArrayList(
        listOf(
            WorkoutSet(0, "First Workout Set", LocalDate.now(), ArrayList(
                listOf(
                    WorkoutExercise(0,"Squats", 20, 4, 0.0f, WorkoutExerciseType.LEGS),
                    WorkoutExercise( 1, "Lunges", 16, 6, 0.0f, WorkoutExerciseType.LEGS),
                    WorkoutExercise( 2, "Push-ups", 14, 4, 0.0f, WorkoutExerciseType.CORE),
                    WorkoutExercise( 3, "Dumbbell Rows", 10, 5, 5.0f, WorkoutExerciseType.BACK)
                ))
            ),
            WorkoutSet(1, "Second Workout Set", LocalDate.of(2021, 11, 1), ArrayList()),
            WorkoutSet(2, "Third Workout Set", LocalDate.of(2021, 10, 30), ArrayList()),
        )
    )

    override fun findAll(): List<WorkoutSet> {
        return workoutSets
    }

    override fun findOne(position: Int): WorkoutSet {
        return workoutSets[position]
    }

    override fun add(workoutSet: WorkoutSet) {
        workoutSets.add(workoutSet)
    }

    override fun remove(position: Int) {
        workoutSets.removeAt(position)
    }
}