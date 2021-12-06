package com.example.laborator2_ma.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.laborator2_ma.databinding.ActivityMainBinding
import com.example.laborator2_ma.databinding.WorkoutSetBinding
import com.example.laborator2_ma.dialogs.DeleteDialogFragment
import com.example.laborator2_ma.domain.WorkoutSet
import com.example.laborator2_ma.utils.toast
import com.example.laborator2_ma.viewmodels.WorkoutViewModel

class MainActivity : AppCompatActivity(), DeleteDialogFragment.DeleteDialogListener {

    companion object {
        const val MAIN_ACTIVITY_WORKOUT_SET_ID = "id"
        const val MAIN_ACTIVITY_WORKOUT_SET_NAME = "name"
        const val MAIN_ACTIVITY_CREATE_WORKOUT = "Create Workout"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var workoutViewModel: WorkoutViewModel

    private val adapter = WorkoutSetAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonAddWorkoutSet.setOnClickListener {
            val intent = Intent(this, WorkoutExerciseActivity::class.java)
            intent.putExtra(MAIN_ACTIVITY_CREATE_WORKOUT, true)
            startActivity(intent)
        }
        binding.workoutSets.adapter = adapter

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.workoutSets.observe(this, {
            it?.let {
                adapter.setWorkoutSetsList(it)
            }
        })
    }

    inner class WorkoutSetAdapter : RecyclerView.Adapter<WorkoutSetViewHolder>() {
        var workoutSets = mutableListOf<WorkoutSet>()

        var selectedWorkoutSetId = 0

        @SuppressWarnings("NotifyDataSetChanged")
        fun setWorkoutSetsList(workoutSets: List<WorkoutSet>) {
            this.workoutSets = workoutSets.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutSetViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = WorkoutSetBinding.inflate(inflater, parent, false)
            return WorkoutSetViewHolder(binding)
        }

        @SuppressWarnings("NotifyDataSetChanged")
        override fun onBindViewHolder(holder: WorkoutSetViewHolder, position: Int) {
            val workoutSet = workoutSets[position]
            holder.binding.workoutSetName.text = workoutSet.name
            holder.binding.workoutSetDate.text = workoutSet.getFormattedDate()
            holder.binding.workoutSetDeleteButton.setOnClickListener {
                selectedWorkoutSetId = workoutSet.workoutSetId
                DeleteDialogFragment().show(supportFragmentManager, "")
            }
            holder.binding.workoutSetCardView.setOnClickListener {
                val intent = Intent(this@MainActivity, WorkoutExerciseActivity::class.java)
                intent.putExtra(MAIN_ACTIVITY_WORKOUT_SET_ID, workoutSet.workoutSetId)
                intent.putExtra(MAIN_ACTIVITY_WORKOUT_SET_NAME, workoutSet.name)
                intent.putExtra(MAIN_ACTIVITY_CREATE_WORKOUT, false)

                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return workoutSets.size
        }
    }

    inner class WorkoutSetViewHolder(val binding: WorkoutSetBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    override fun apply(toDelete: Boolean) {
        if (toDelete) {
            workoutViewModel.deleteWorkoutExercisesForSet(adapter.selectedWorkoutSetId)
            workoutViewModel.deleteWorkoutSet(adapter.selectedWorkoutSetId)
            toast("Workout Set deleted successfully")
        }
    }
}