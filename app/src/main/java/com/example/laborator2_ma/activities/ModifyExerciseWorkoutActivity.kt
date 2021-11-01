package com.example.laborator2_ma.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.laborator2_ma.databinding.ModifyActivityBinding
import com.example.laborator2_ma.dependencyinjection.ApplicationContainer
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.utils.logd
import com.example.laborator2_ma.utils.toast

class ModifyExerciseWorkoutActivity : AppCompatActivity() {

    companion object {
        const val EXERCISE_ACTIVITY_POSITION = "Position"
        const val EXERCISE_ACTIVITY_ID = "Id"
        const val EXERCISE_ACTIVITY_NAME = "Name"
        const val EXERCISE_ACTIVITY_NUMBER_OF_SETS = "Number of Sets"
        const val EXERCISE_ACTIVITY_NUMBER_OF_REPS = "Number of Reps"
        const val EXERCISE_ACTIVITY_WEIGHT = "Weight"
        const val EXERCISE_ACTIVITY_TYPE = "Type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ModifyActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        var id: Int = -1
        var position = 0
        var workoutSetId: Int
        var workoutSetExercises = ArrayList<WorkoutExercise>()
        if (bundle != null) {
            logd("Workout ID ${bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)}")
            logd("Position: ${bundle.getInt(EXERCISE_ACTIVITY_POSITION)}")
            logd("Id: ${bundle.getInt(EXERCISE_ACTIVITY_ID)}")
            logd("Name: ${bundle.getString(EXERCISE_ACTIVITY_NAME)}")
            logd("Number of sets: ${bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_SETS)}")
            logd("Number of reps: ${bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_REPS)}")
            logd("Weight: ${bundle.getFloat(EXERCISE_ACTIVITY_WEIGHT)}")
            logd("Type: ${bundle.getString(EXERCISE_ACTIVITY_TYPE)}")

            position = bundle.getInt(EXERCISE_ACTIVITY_POSITION)
            id = bundle.getInt(EXERCISE_ACTIVITY_ID)
            workoutSetId = bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)
            workoutSetExercises = ApplicationContainer.workoutSetRepository.findOne(workoutSetId).exercises

            binding.editTextName.setText(bundle.getString(EXERCISE_ACTIVITY_NAME))
            binding.editTextNumberOfSets.setText(bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_SETS).toString())
            binding.editTextNumberOfReps.setText(bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_REPS).toString())
            binding.editTextWeight.setText(bundle.getFloat(EXERCISE_ACTIVITY_WEIGHT).toString())
            binding.editTextExerciseType.setText(bundle.getString(EXERCISE_ACTIVITY_TYPE))
        }
        binding.modifyButton.setOnClickListener {
            toast("Exercise has been modified successfully!")

            val name = binding.editTextName.text.toString()
            val numberOfSets = binding.editTextNumberOfSets.text.toString().toInt()
            val numberOfReps = binding.editTextNumberOfReps.text.toString().toInt()
            val weight = binding.editTextWeight.text.toString().toFloat()
            val exerciseType = enumValueOf<WorkoutExerciseType>(binding.editTextExerciseType.text.toString().uppercase())

            val workoutExercise = WorkoutExercise(id, name, numberOfReps, numberOfSets, weight, exerciseType)
            workoutSetExercises[position] = workoutExercise

            val response = Intent()
            response.putExtra(EXERCISE_ACTIVITY_POSITION, position)
            response.putExtra(EXERCISE_ACTIVITY_ID, workoutExercise.id)
            response.putExtra(EXERCISE_ACTIVITY_NAME, workoutExercise.name)
            response.putExtra(EXERCISE_ACTIVITY_NUMBER_OF_REPS, workoutExercise.numberOfReps)
            response.putExtra(EXERCISE_ACTIVITY_NUMBER_OF_SETS, workoutExercise.numberOfSets)
            response.putExtra(EXERCISE_ACTIVITY_WEIGHT, workoutExercise.weight)
            response.putExtra(EXERCISE_ACTIVITY_TYPE, workoutExercise.exerciseType.toString())
            setResult(Activity.RESULT_OK, response)
            finish()
        }
    }
}