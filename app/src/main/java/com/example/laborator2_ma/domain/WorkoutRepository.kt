package com.example.laborator2_ma.domain

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.laborator2_ma.database.WorkoutDao

class WorkoutRepository(private val workoutDao: WorkoutDao) {

    val workoutSets: LiveData<List<WorkoutSet>> = workoutDao.getWorkoutSets()

    fun getWorkoutExercises(workoutSetId: Int): LiveData<List<WorkoutExercise>> =
        workoutDao.getWorkoutExercises(workoutSetId)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addWorkoutSet(workoutSet: WorkoutSet) {
        workoutDao.addWorkoutSet(workoutSet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addWorkoutExercise(workoutExercise: WorkoutExercise) {
        workoutDao.addWorkoutExercise(workoutExercise)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWorkoutSet(workoutSetId: Int) {
        workoutDao.deleteWorkoutSet(workoutSetId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWorkoutExercisesForSet(workoutSetId: Int) {
        workoutDao.deleteAllWorkoutExercisesForSet(workoutSetId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteWorkoutExercise(workoutExerciseId: Int) {
        workoutDao.deleteWorkoutExercise(workoutExerciseId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise) {
        workoutDao.updateWorkoutExercise(workoutExercise)
    }
}