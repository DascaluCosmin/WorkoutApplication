package com.example.laborator2_ma.domain

import java.time.LocalDate

data class WorkoutSet(
    private var id: Int,
    private var name: String,
    private var createdAt: LocalDate,
    private var exercises: List<WorkoutExercise>
)