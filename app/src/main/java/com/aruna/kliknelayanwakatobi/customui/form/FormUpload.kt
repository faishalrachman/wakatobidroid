package com.aruna.kliknelayanwakatobi.customui.form

import ControllerDate
import ControllerFile
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.aruna.kliknelayanwakatobi.R
import com.aruna.kliknelayanwakatobi.tools.*


/**
 * Created by marzellaalfamega on 12/5/17.
 */
class FormUpload(context: Context, title: String) : FormBase(context, title) {

    var photoFileName = ""
    var photoLink = ""

    var tvFileName: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.form_upload, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitleFormUpload)
        tvTitle.text = title

        val btnBrowse = view.findViewById<Button>(R.id.btnBrowseFormUpload)
        btnBrowse.setOnClickListener { openBrowsePicture() }

        tvFileName = view.findViewById(R.id.tvLinkUploadFormUpload)

        tvFileName.text = "file name"

        addView(view)
    }

    private fun openBrowsePicture() {
        Utils.Log("Form Upload", "${ControllerDate.currentTimeStamp}WKP.jpg")
        photoFileName = "${ControllerDate.currentTimeStamp}WKP.jpg"
        Utils.Log("Form Upload", "Photo name ${photoFileName}")
        val cameraIntent = Utils.createCameraIntent(context, photoFileName)
        if (cameraIntent?.resolveActivity(context.packageManager) != null) {
            startActivityForResult(context as AppCompatActivity, cameraIntent, REQUEST_IMAGE_CAPTURE, null)
        }
    }

    fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Utils.Log("Form Upload", "1")
        if (resultCode == Activity.RESULT_OK) {
            Utils.Log("Form Upload", "2")
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Utils.Log("Form Upload", "3")
                tvFileName.text = photoFileName
//                uploadFile(object : TransferListener {
//                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
//                        Utils.Log("progress", "bytes current : $bytesCurrent / $bytesTotal")
//                    }
//
//                    override fun onStateChanged(id: Int, state: TransferState?) {
//                        Utils.Log("progress", "state : $state")
//                    }
//
//                    override fun onError(id: Int, ex: Exception?) {
//                        Utils.Log("progress", "ex : ${ex?.message ?: "empty message"}")
//                    }
//
//                })
            }
        }
    }

    //todo upload file need cognito identity
    fun uploadFile(listener: TransferListener) {

        val credentialsProvider = CognitoCachingCredentialsProvider(
                context, /* get the context for the application */
                COGNITO_IDENTITY_POOL, /* Identity Pool ID */
                Regions.fromName(COGNITO_REGION)           /* Region for your identity pool--US_EAST_1 or EU_WEST_1*/
        )

        val s3 = AmazonS3Client(credentialsProvider)
        val transferUtility = TransferUtility(s3, context)
        val fileUpload = ControllerFile.getDefaultFolder(filename = photoFileName)
        Utils.Log("file to upload", fileUpload?.absolutePath ?: "path kosong")
        val observer = transferUtility.upload(
                S3_BUCKET, /* The bucket to upload to */
                "fish/$photoFileName", /* The key for the uploaded object */
                fileUpload        /* The file where the data to upload exists */
        )

        observer.setTransferListener(listener)
    }

    override fun getValue(): String {
        return photoLink
    }

    override fun isSkipCheck(): Boolean {
        return true
    }
}