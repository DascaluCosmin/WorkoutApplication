package com.example.laborator2_ma.dependencyinjection

import com.example.laborator2_ma.repository.WorkoutSetRepository
import com.example.laborator2_ma.repository.WorkoutSetRepositoryInMemory

class ApplicationContainer {

    companion object {
        var workoutSetRepository: WorkoutSetRepository = WorkoutSetRepositoryInMemory()
    }
}