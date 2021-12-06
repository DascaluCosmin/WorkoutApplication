package com.example.laborator2_ma.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutSet

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout_set")
    fun getWorkoutSets(): LiveData<List<WorkoutSet>>

    @Query("SELECT * FROM workout_set WHERE workoutSetId = :workoutSetId")
    fun getWorkoutSet(workoutSetId: Int): WorkoutSet

    @Query(
        """
        SELECT workout_exercise.* FROM workout_exercise
        INNER JOIN workout_set ON workoutSetId = workout_set_id
        WHERE workoutSetId = :workoutSetIdSearch 
    """
    )
    fun getWorkoutExercises(workoutSetIdSearch: Int): LiveData<List<WorkoutExercise>>

    @Query("SELECT * FROM workout_exercise WHERE workout_set_id = :workoutSetId")
    fun getWorkoutExercisesByWorkoutSet(workoutSetId: Int): LiveData<List<WorkoutExercise>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWorkoutSet(workoutSet: WorkoutSet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWorkoutExercise(workoutExercise: WorkoutExercise)

    @Query("DELETE FROM workout_set WHERE workoutSetId = :workoutSetId")
    fun deleteWorkoutSet(workoutSetId: Int)

    @Query("DELETE FROM workout_exercise WHERE workout_set_id = :workoutSetId")
    fun deleteAllWorkoutExercisesForSet(workoutSetId: Int)

    @Query("DELETE FROM workout_exercise WHERE workoutExerciseId = :workoutExerciseId")
    fun deleteWorkoutExercise(workoutExerciseId: Int)

    @Update
    fun updateWorkoutExercise(workoutExercise: WorkoutExercise)
}