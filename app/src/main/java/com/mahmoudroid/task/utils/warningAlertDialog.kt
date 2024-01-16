package com.mahmoudroid.task.utils

import android.content.Context
import android.content.DialogInterface

fun Context.warningAlertDialog(context: Context, message: String?) {
    val builder = android.app.AlertDialog.Builder(context)
    builder.setTitle("Sorry")
    builder.setMessage(message)
    builder.setNegativeButton("OK") { dialog: DialogInterface, which: Int ->
        //        dialog.cancel()
//        requireActivity().finish()
    }
    builder.show()
}