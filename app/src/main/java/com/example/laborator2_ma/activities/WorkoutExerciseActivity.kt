package com.example.laborator2_ma.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.laborator2_ma.databinding.WorkoutExerciseActivityBinding
import com.example.laborator2_ma.databinding.WorkoutExerciseBinding
import com.example.laborator2_ma.dialogs.DeleteDialogFragment
import com.example.laborator2_ma.domain.*
import com.example.laborator2_ma.utils.toast
import com.example.laborator2_ma.viewmodels.WorkoutViewModel
import java.util.*


class WorkoutExerciseActivity : AppCompatActivity(), DeleteDialogFragment.DeleteDialogListener {

    private lateinit var binding: WorkoutExerciseActivityBinding
    private lateinit var workoutViewModel: WorkoutViewModel

    private val adapter = ExerciseWorkoutAdapter()
    private var currentWorkoutSetId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WorkoutExerciseActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        binding.workoutExercises.adapter = adapter

        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val isNewWorkout = bundle.getBoolean(MainActivity.MAIN_ACTIVITY_CREATE_WORKOUT)
            if (!isNewWorkout) {
                currentWorkoutSetId = bundle.getInt(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID)

                workoutViewModel.getWorkoutExercises(currentWorkoutSetId).observe(this) {
                    it?.let {
                        adapter.setWorkoutExercisesList(it)
                    }
                }
                val currentWorkoutSetName =
                    bundle.getString(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_NAME)

                binding.workoutSetNameEditText.setText(currentWorkoutSetName ?: "Undefined")
                binding.workoutSetNameEditText.keyListener = null
                binding.buttonAddSetWorkout.visibility = View.INVISIBLE
            } else {
                binding.buttonAddExerciseWorkout.visibility = View.INVISIBLE
            }
        }

        binding.buttonAddExerciseWorkout.setOnClickListener {
            val intent = Intent(this, AddExerciseWorkoutActivity::class.java)
            intent.putExtra(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID, currentWorkoutSetId)
            startActivity(intent)
        }

        binding.buttonAddSetWorkout.setOnClickListener {
            val workoutName = binding.workoutSetNameEditText.text.toString()
            if (workoutName.isNotEmpty()) {
                workoutViewModel.addWorkoutSet(WorkoutSet(0, workoutName, Date()))
                finish()
            } else {
                toast("Please introduce a workout name")
            }
        }
    }

    inner class ExerciseWorkoutAdapter : RecyclerView.Adapter<ExerciseWorkoutViewHolder>() {

        var workoutExercises = mutableListOf<WorkoutExercise>()
        var selectedWorkoutExerciseId = 0

        @SuppressWarnings("NotifyDataSetChanged")
        fun setWorkoutExercisesList(workoutExercises: List<WorkoutExercise>) {
            this.workoutExercises = workoutExercises.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ExerciseWorkoutViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = WorkoutExerciseBinding.inflate(inflater, parent, false)
            return ExerciseWorkoutViewHolder(binding)
        }

        @SuppressWarnings("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: ExerciseWorkoutViewHolder, position: Int) {
            val workoutExercise = workoutExercises[position]

            holder.binding.workoutExerciseName.text = workoutExercise.name
            holder.binding.workoutExerciseNumberOfSets.text =
                workoutExercise.numberOfSets.toString()
            holder.binding.exerciseWorkoutNumberOfReps.text =
                workoutExercise.numberOfReps.toString()
            holder.binding.workoutExerciseWeight.text = workoutExercise.weight.toString()
            holder.binding.exerciseWorkoutType.text = workoutExercise.exerciseType.toString()
            holder.binding.deleteButton.setOnClickListener {
                selectedWorkoutExerciseId = workoutExercise.workoutExerciseId
                DeleteDialogFragment().show(supportFragmentManager, "")
            }
            holder.binding.modifyButton.setOnClickListener {
                val intent =
                    Intent(this@WorkoutExerciseActivity, ModifyExerciseWorkoutActivity::class.java)
                intent.putExtra(MainActivity.MAIN_ACTIVITY_WORKOUT_SET_ID, currentWorkoutSetId)
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_ID,
                    workoutExercise.workoutExerciseId
                )
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NAME,
                    workoutExercise.name
                )
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_SETS,
                    workoutExercise.numberOfSets
                )
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_NUMBER_OF_REPS,
                    workoutExercise.numberOfReps
                )
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_WEIGHT,
                    workoutExercise.weight
                )
                intent.putExtra(
                    ModifyExerciseWorkoutActivity.EXERCISE_ACTIVITY_TYPE,
                    workoutExercise.exerciseType.toString()
                )
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return workoutExercises.size
        }
    }

    inner class ExerciseWorkoutViewHolder(val binding: WorkoutExerciseBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    override fun apply(toDelete: Boolean) {
        if (toDelete) {
            workoutViewModel.deleteWorkoutExercise(adapter.selectedWorkoutExerciseId)
            toast("Workout Exercise deleted successfully")
        }
    }
}