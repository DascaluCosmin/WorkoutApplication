package com.example.laborator2_ma.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laborator2_ma.databinding.AddActivityBinding
import com.example.laborator2_ma.dependencyinjection.ApplicationContainer
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.repository.WorkoutExerciseRepository
import com.example.laborator2_ma.utils.toast

class AddExerciseWorkoutActivity : AppCompatActivity() {

    private val applicationContainer = ApplicationContainer()
    private val workoutExerciseRepository: WorkoutExerciseRepository = applicationContainer.getSingletonWorkoutExerciseRepository()!!

    companion object {
        const val EXERCISE_ACTIVITY_ID = "Id"
        const val EXERCISE_ACTIVITY_NAME = "Name"
        const val EXERCISE_ACTIVITY_NUMBER_OF_SETS = "Number of Sets"
        const val EXERCISE_ACTIVITY_NUMBER_OF_REPS = "Number of Reps"
        const val EXERCISE_ACTIVITY_WEIGHT = "Weight"
        const val EXERCISE_ACTIVITY_TYPE = "Type"
    }

    private lateinit var binding: AddActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            toast("Exercise has been added successfully!")
            val name = binding.addWorkoutExerciseNameEditText.text.toString()
            val numberOfSets = binding.addWorkoutExerciseNumberOfSetsEditText.text.toString().toInt()
            val numberOfReps = binding.addWorkoutExerciseNumberOfRepsEditText.text.toString().toInt()
            val weight = binding.addWorkoutExerciseWeightEditText.text.toString().toFloat()

            // TODO: Add validation to this
            val exerciseType = enumValueOf<WorkoutExerciseType>(binding.addWorkoutExerciseTypeEditText.text.toString().uppercase())

            val workoutExercise = WorkoutExercise(name, numberOfReps, numberOfSets, weight, exerciseType)
            workoutExercise.id = workoutExerciseRepository.add(workoutExercise)

            val response = Intent()
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_ID, workoutExercise.id)
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NAME, workoutExercise.name)
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_REPS, workoutExercise.numberOfReps)
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_SETS, workoutExercise.numberOfSets)
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_WEIGHT, workoutExercise.weight)
            response.putExtra(ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_TYPE, workoutExercise.exerciseType.toString())
            setResult(Activity.RESULT_OK, response)
            finish()
        }
    }
}