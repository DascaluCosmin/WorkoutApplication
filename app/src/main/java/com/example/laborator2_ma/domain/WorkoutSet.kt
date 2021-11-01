package com.example.laborator2_ma.domain

import java.time.LocalDate

data class WorkoutSet(
    var id: Int,
    var name: String,
    var createdAt: LocalDate,
    var exercises: ArrayList<WorkoutExercise>
) {
    constructor(name: String, createdAt: LocalDate, exercises: ArrayList<WorkoutExercise>): this(0, name, createdAt, exercises)
}