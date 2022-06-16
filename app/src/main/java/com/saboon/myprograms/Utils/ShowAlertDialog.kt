package com.saboon.myprograms.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.saboon.myprograms.R

class ShowAlertDialog(private val activity: Activity,private val context: Context) {



    fun showDeleteAlert(title: String,message: String, response: (Boolean) -> Unit){

        val alertDialogBuilder = AlertDialog.Builder(activity)

        val string = activity.resources


        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setPositiveButton(string.getString(R.string.alertDialog_posButton)) { dialog, id ->
            response(true)
        }
        alertDialogBuilder.setNegativeButton(string.getString(R.string.alertDialog_negButton)){ dialog, id ->
            response(false)
        }

        alertDialogBuilder.show()
    }

    fun showDeleteAlertDialog(title: String,message: String, response: (Boolean) -> Unit){

        val string = activity.resources

        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(string.getString(R.string.alertDialog_negButton)){dialog, which ->
                response(false)
            }
            .setPositiveButton(string.getString(R.string.alertDialog_posButton)){dialog, which ->
                response(true)
            }
            .show()
    }
}