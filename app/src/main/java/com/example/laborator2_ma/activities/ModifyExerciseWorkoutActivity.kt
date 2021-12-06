package com.example.laborator2_ma.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.laborator2_ma.databinding.ModifyActivityBinding
import com.example.laborator2_ma.domain.WorkoutExercise
import com.example.laborator2_ma.domain.WorkoutExerciseType
import com.example.laborator2_ma.utils.toast
import com.example.laborator2_ma.viewmodels.WorkoutViewModel

class ModifyExerciseWorkoutActivity : AppCompatActivity() {

    companion object {
        const val EXERCISE_ACTIVITY_ID = "Id"
        const val EXERCISE_ACTIVITY_NAME = "Name"
        const val EXERCISE_ACTIVITY_NUMBER_OF_SETS = "Number of Sets"
        const val EXERCISE_ACTIVITY_NUMBER_OF_REPS = "Number of Reps"
        const val EXERCISE_ACTIVITY_WEIGHT = "Weight"
        const val EXERCISE_ACTIVITY_TYPE = "Type"
    }

    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ModifyActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        val bundle: Bundle? = intent.extras
        var id: Int = -1
        var workoutSetId = 0
        if (bundle != null) {
            id = bundle.getInt(EXERCISE_ACTIVITY_ID)
            workoutSetId = bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)

            binding.editTextName.setText(bundle.getString(EXERCISE_ACTIVITY_NAME))
            binding.editTextNumberOfSets.setText(
                bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_SETS).toString()
            )
            binding.editTextNumberOfReps.setText(
                bundle.getInt(EXERCISE_ACTIVITY_NUMBER_OF_REPS).toString()
            )
            binding.editTextWeight.setText(bundle.getFloat(EXERCISE_ACTIVITY_WEIGHT).toString())
            binding.editTextExerciseType.setText(bundle.getString(EXERCISE_ACTIVITY_TYPE))
        }
        binding.modifyButton.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val numberOfSets = binding.editTextNumberOfSets.text.toString().toInt()
            val numberOfReps = binding.editTextNumberOfReps.text.toString().toInt()
            val weight = binding.editTextWeight.text.toString().toFloat()
            val exerciseType = enumValueOf<WorkoutExerciseType>(
                binding.editTextExerciseType.text.toString().uppercase()
            )

            workoutViewModel.updateWorkoutExercise(
                WorkoutExercise(
                    id,
                    name,
                    numberOfReps,
                    numberOfSets,
                    weight,
                    exerciseType,
                    workoutSetId
                )
            )
            toast("Exercise has been modified successfully!")
            finish()
        }
    }
}