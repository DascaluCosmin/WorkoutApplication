package com.example.laborator2_ma.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.laborator2_ma.database.WorkoutDatabase
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutRepository
import com.example.laborator2_ma.domain.WorkoutSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * If you have time, try playing with MutableLiveData
 */
class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WorkoutRepository

    val workoutSets: LiveData<List<WorkoutSet>>

    init {
        val workoutDao = WorkoutDatabase.getDatabase(application, viewModelScope).workoutDao()
        repository = WorkoutRepository(workoutDao)
        workoutSets = repository.workoutSets
    }

    fun getWorkoutExercises(workoutSetId: Int): LiveData<List<WorkoutExercise>> =
        repository.getWorkoutExercises(workoutSetId)

    fun addWorkoutSet(workoutSet: WorkoutSet) = viewModelScope.launch(Dispatchers.IO) {
        repository.addWorkoutSet(workoutSet)
    }

    fun addWorkoutExercise(workoutExercise: WorkoutExercise) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWorkoutExercise(workoutExercise)
        }

    fun deleteWorkoutSet(workoutSetId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteWorkoutSet(workoutSetId)
    }

    fun deleteWorkoutExercisesForSet(workoutSetId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteWorkoutExercisesForSet(workoutSetId)
    }

    fun deleteWorkoutExercise(workoutExerciseId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteWorkoutExercise(workoutExerciseId)
    }

    fun updateWorkoutExercise(workoutExercise: WorkoutExercise) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWorkoutExercise(workoutExercise)
        }
}