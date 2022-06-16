package com.saboon.myprograms.Utils

import android.app.Activity
import android.app.AlertDialog
import com.saboon.myprograms.R

class ShowAlertDialog(private val activity: Activity) {



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
}