package com.example.laborator2_ma.repository

import com.example.laborator2_ma.domain.WorkoutSet

interface WorkoutSetRepository {

    fun findAll(): List<WorkoutSet>

    fun findOne(position: Int): WorkoutSet

    fun add(workoutSet: WorkoutSet)

    fun remove(position: Int)
}