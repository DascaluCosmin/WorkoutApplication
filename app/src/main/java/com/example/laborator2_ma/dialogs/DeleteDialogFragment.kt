package com.example.laborator2_ma.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.laborator2_ma.R
import java.lang.IllegalArgumentException

class DeleteDialogFragment : DialogFragment() {

    lateinit var deleteDialogListener: DeleteDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.deleteDialogTitle)
                .setPositiveButton(R.string.positiveAnswer) { _, _ ->
                    deleteDialogListener.apply(true)
                }
                .setNegativeButton(R.string.negativeAnswer) { _, _ ->
                    deleteDialogListener.apply(false)
                }
            builder.create()
        } ?: throw IllegalArgumentException("Activity can't be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        deleteDialogListener = context as DeleteDialogListener
    }

    interface DeleteDialogListener {
        fun apply(toDelete: Boolean)
    }
}