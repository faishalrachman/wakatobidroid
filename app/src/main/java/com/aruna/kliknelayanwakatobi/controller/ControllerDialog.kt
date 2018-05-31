import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.aruna.kliknelayanwakatobi.R

/**
 * Created by IT11 on 6/9/2015.
 *
 */
object ControllerDialog {

    fun showLoading(context: Context): ProgressDialog =
            ProgressDialog.show(context, "Loading...", "Loading Data.")

    fun showLoadingNotCancelable(context: Context): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Loading...")
        progressDialog.setMessage("Loading Data.")
        return progressDialog
    }

//    fun showLoadingNotCancelable(context: Context, title: CharSequence, msg: CharSequence): ProgressDialog {
//        val progressDialog = ProgressDialog(context)
//        progressDialog.setCancelable(false)
//        progressDialog.setTitle(title)
//        progressDialog.setMessage(msg)
//        progressDialog.show()
//        return progressDialog
//    }

    fun showYesNoDialog(message: String, context: Context, listener: DialogInterface.OnClickListener): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.btn_positive_yes).toUpperCase(), listener)
                .setNegativeButton(context.getString(R.string.btn_negative_no).toUpperCase(), listener)
        return builder.show()
    }

    fun showTwoOptionsDialog(message: String, context: Context,
                             listener: DialogInterface.OnClickListener,
                             yesOption: String, noOption: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
                .setPositiveButton(yesOption.toUpperCase(), listener)
                .setNegativeButton(noOption.toUpperCase(), listener)

        return builder.show()
    }

    fun showDialogInfo(title: String, message: String, context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.btn_ok)
                ) { dialog, which -> dialog.dismiss() }

        return builder.show()
    }

    fun showDialogCloseActivity(title: String, message: String, context: AppCompatActivity): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.btn_ok)
                ) { dialog, which ->
                    dialog.dismiss()
                    val intent = Intent()//if (data.hasExtra("refresh")) {
                    intent.putExtra("refresh", true)
                    context.setResult(Activity.RESULT_OK, intent)
                    context.finish()
                }

        return builder.show()
    }

    fun showDialogToSetting(title: String, message: String, context: Context, listener: DialogInterface.OnClickListener): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
                .setPositiveButton("Settings", listener)

        return builder.show()
    }

//    fun showDialogUpdate(context: Context, forceUpdate: Int, msg: String, listener: DialogInterface.OnClickListener): AlertDialog {
//
//        val builder = AlertDialog.Builder(context)
//        builder.setMessage(msg)
//                .setPositiveButton(context.getString(R.string.btn_positive_update).toUpperCase(), listener)
//                .setCancelable(false)
//
//        if (forceUpdate == 0)
//            builder.setNegativeButton(context.getString(R.string.btn_negative_ignore).toUpperCase(), listener)
//
//        return builder.show()
//    }

//    fun showInputDialog(
//            dialogTitle: CharSequence,
//            inputText: CharSequence,
//            inputTextHint: CharSequence,
//            inputType: Int,
//            context: Context, listener: InputDialogListener): AlertDialog {
//
//        val layoutInflaterAndroid = LayoutInflater.from(context)
//        val mView = layoutInflaterAndroid.inflate(R.layout.dialog_input_text, null)
//
//        val title = mView.findViewById<View>(R.id.title) as TextView
//        val textViewHelper = mView.findViewById<View>(R.id.textViewHelper) as TextView
//        val userInputDialogEditText = mView.findViewById<View>(R.id.userInputDialog) as EditText
//
//        title.text = dialogTitle
//
//        if (inputType == InputType.TYPE_CLASS_NUMBER) {
//            userInputDialogEditText.setSelectAllOnFocus(true)
//        }
//
//        userInputDialogEditText.setText(inputText)
//        userInputDialogEditText.requestFocusFromTouch()
//        userInputDialogEditText.requestFocus()
//        userInputDialogEditText.setRawInputType(inputType)
//        textViewHelper.text = inputTextHint
//
//        val builder = AlertDialog.Builder(context)
//        builder.setCancelable(false)
//        builder.setTitle(null)
//        builder.setView(mView)
//
//        // Set up the buttons
//        builder.setPositiveButton(context.getString(R.string.btn_positive_ok)) { dialog, which ->
//            listener.onInputTextDone(userInputDialogEditText.text)
//
//            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(userInputDialogEditText.windowToken, 0)
//
//            dialog.dismiss()
//        }
//
//        builder.setNegativeButton(context.getString(R.string.btn_negative_cancel)) { dialog, which ->
//            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(userInputDialogEditText.windowToken, 0)
//
//            dialog.dismiss()
//        }
//
//        // Force keyboard to show
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
//
//
//        return builder.show()
//    }
}
