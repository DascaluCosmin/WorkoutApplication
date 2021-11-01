package com.example.laborator2_ma.dependencyinjection

import com.example.laborator2_ma.repository.WorkoutExerciseRepository
import com.example.laborator2_ma.repository.WorkoutExerciseRepositoryInMemory
import com.example.laborator2_ma.repository.WorkoutSetRepository
import com.example.laborator2_ma.repository.WorkoutSetRepositoryInMemory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationContainer {

    companion object {
        var workoutExerciseRepository: WorkoutExerciseRepository? = null // TODO: Change this
        var workoutSetRepository: WorkoutSetRepository = WorkoutSetRepositoryInMemory()
    }

    fun getSingletonWorkoutExerciseRepository(): WorkoutExerciseRepository? {
        if (workoutExerciseRepository == null) {
            workoutExerciseRepository = WorkoutExerciseRepositoryInMemory()
        }
        return workoutExerciseRepository
    }

    @Provides
    @Singleton
    fun provideWorkoutExerciseRepository(repository: WorkoutExerciseRepositoryInMemory): WorkoutExerciseRepository {
        return WorkoutExerciseRepositoryInMemory()
    }
}