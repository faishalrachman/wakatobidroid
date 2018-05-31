import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

/**
 * Created by marzellaalfamega on 12/29/16. <br></br>
 * Manage Local Storage file on device storage.
 */
object ControllerFile {

    private val folderName = ".wakatobi"

    fun getDefaultPath(fileName: String): String? {
        val destFolder = File(Environment.getExternalStorageDirectory(), folderName)
        if (!destFolder.exists()) {
            if (!destFolder.mkdir()) {
                return null
            }
        }
        return if (TextUtils.isEmpty(fileName)) {
            destFolder.absolutePath
        } else {
            destFolder.absolutePath + "/" + fileName
        }
    }

    fun getDefaultFolder(filename: String = ""): File? {
        val destFolder = File(Environment.getExternalStorageDirectory(), folderName)
        if (!destFolder.exists()) {
            if (!destFolder.mkdir()) {
                return null
            }
        }
        return File(destFolder, filename)
    }

    fun getImage(context: Context, photoName: String): Bitmap? {
        try {
            return MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.fromFile(getDefaultFolder(photoName)))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun removeFile(fileName: String?): Boolean {
        if (fileName != null) {
            val allPath = getDefaultPath(fileName)
            if (!TextUtils.isEmpty(allPath)) {
                val fDelete = File(allPath)
                if (fDelete.exists()) {
                    return fDelete.delete()
                }
            }
        }
        return true
    }

    fun saveBitmap(signature: Bitmap): String {
        val filename = ControllerDate.currentTimeStamp.toString() + "S.image"

        val dest = getDefaultFolder(filename)
        return if (dest != null) {
            try {
                val out = FileOutputStream(dest)
                signature.compress(Bitmap.CompressFormat.JPEG, 90, out)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            filename
        } else {
            ""
        }
    }

    fun deleteAllNotTodayData() {
        val folder = File(getDefaultPath("")!!)
        if (folder.isDirectory) {
            val children = folder.list()
            for (aChildren in children) {
                val fDelete = File(folder, aChildren)
                val lastModDate = Date(fDelete.lastModified())
                if (fDelete.exists() && !ControllerDate.isToday(lastModDate)) {
                    fDelete.delete()
                }
            }
        }

        val oldFolder = File(Environment.getExternalStorageDirectory(), "paketDriver")
        if (!oldFolder.exists()) {
            return
        }
        if (oldFolder.isDirectory) {
            val children = oldFolder.list()

            for (aChildren in children) {
                val fDelete = File(oldFolder, aChildren)
                fDelete.delete()
            }
//            children
//                    .map { File(oldFolder, it) }
//                    .forEach { it.delete() }
        }
        oldFolder.delete()
    }

    fun createTempFile(fileName: String): File {
        return File.createTempFile(
                fileName,
                ".image",
                getDefaultFolder()
        )
    }

//    companion object {
//
//        private var instance: ControllerFile? = null
//        private val folderName = ".paketDriver"
//
//        fun getInstance(): ControllerFile {
//            if (instance == null) {
//                instance = ControllerFile()
//            }
//            return instance
//        }
//    }
}