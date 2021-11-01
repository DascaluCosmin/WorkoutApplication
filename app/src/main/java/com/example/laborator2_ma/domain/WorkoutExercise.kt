package com.example.laborator2_ma.domain

data class WorkoutExercise(
    var id: Int,
    var name: String,
    var numberOfReps: Int,
    var numberOfSets: Int,
    var weight: Float,
    var exerciseType: WorkoutExerciseType
) {
    constructor(name: String, numberOfReps: Int, numberOfSets: Int, weight: Float, exerciseType: WorkoutExerciseType):
            this(0, name, numberOfReps, numberOfSets, weight, exerciseType)
}