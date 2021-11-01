package com.example.laborator2_ma.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laborator2_ma.databinding.AddActivityBinding
import com.example.laborator2_ma.dependencyinjection.ApplicationContainer
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.utils.toast

class AddExerciseWorkoutActivity : AppCompatActivity() {

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

        val bundle: Bundle? = intent.extras
        var workoutSetId: Int
        var workoutSetExercises = ArrayList<WorkoutExercise>()
        if (bundle != null) {
            workoutSetId = bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)
            workoutSetExercises = ApplicationContainer.workoutSetRepository.findOne(workoutSetId).exercises
        }

        binding = AddActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddExerciseWorkout.setOnClickListener {
            toast("Exercise has been added successfully!")
            val name = binding.addWorkoutExerciseNameEditText.text.toString()
            val numberOfSets = binding.addWorkoutExerciseNumberOfSetsEditText.text.toString().toInt()
            val numberOfReps = binding.addWorkoutExerciseNumberOfRepsEditText.text.toString().toInt()
            val weight = binding.addWorkoutExerciseWeightEditText.text.toString().toFloat()

            // TODO: Add validation to this
            val exerciseType = enumValueOf<WorkoutExerciseType>(binding.addWorkoutExerciseTypeEditText.text.toString().uppercase())

            val workoutExercise = WorkoutExercise(name, numberOfReps, numberOfSets, weight, exerciseType)

            workoutSetExercises.add(workoutExercise)
            workoutExercise.id = workoutSetExercises.size - 1

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