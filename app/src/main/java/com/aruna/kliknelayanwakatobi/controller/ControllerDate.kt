
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by marzellaalfamega on 12/29/16.
 *
 * Controll date
 */

object ControllerDate {

    val defaultLocale = Locale.getDefault()

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
    private val dateFormatCOD = SimpleDateFormat("yyyy-MM-dd HH:mm")
    private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val dateFormatPOD = SimpleDateFormat("dd MMM HH:mm")
    private val dateFormatDonePOD = SimpleDateFormat("dd MMM yyyy, HH:mm")
    private val dateFormatChat = SimpleDateFormat("h:mm a")
    private val dateFormatPickup = SimpleDateFormat("h:mm a")

    /**
     * Get Locale. When active locale is not Indonesia the set locale to English
     *
     * @return Return English locale when active Locale is not English (US)
     */
    val defLocale: Locale
        get() = if (defaultLocale.country != "ID") {
            Locale.ENGLISH
        } else {
            defaultLocale
        }

    val currentTimeStamp: Long
        get() = Calendar.getInstance().timeInMillis

    val currentDate: String
        get() {
            val calendar = Calendar.getInstance()
            return dateFormat.format(calendar.time)
        }

    fun formatToPodTime(input_on: String): String {
        try {
            val date = dateFormat.parse(input_on)
            return dateFormatPOD.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun formatToDonePodTime(input_on: String): String {
        try {
            val date = dateFormat.parse(input_on)
            return dateFormatDonePOD.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun isToday(stringDate: String): Boolean {
        try {
            val date = Calendar.getInstance()
            val today = Calendar.getInstance()
            date.timeInMillis = dateFormat.parse(stringDate).time
            return date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return true
    }

    fun isToday(proceedDate: Date): Boolean {
        val date = Calendar.getInstance()
        val today = Calendar.getInstance()

        date.timeInMillis = proceedDate.time

        return date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
    }

    fun formatToChatTime(timeStamp: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = timeStamp
        return dateFormatChat.format(date.time)
    }

    fun formatToCodTime(time: String): CharSequence {
        try {
            val date = dateFormat2.parse(time)
            return dateFormatCOD.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return ""
    }

    fun formatToPickupListTime(dateString: String): String {
        try {
            val date = dateFormat.parse(dateString)
            return dateFormatPickup.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

        return ""
    }

//    companion object {
//
//        val defaultLocale = Locale.getDefault()
//
//        private var instance: ControllerDate? = null
//
//        fun getInstance(): ControllerDate {
//            if (instance == null) {
//                instance = ControllerDate()
//            }
//            return instance
//        }
//    }
}
