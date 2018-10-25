package rpg.kotlin.com.esgikotlinrpgandroid.misc

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created on 22/10/2018 by cyrilicard
 *
 */
class DateUtils {

  companion object {
    fun calendarToStringTime(calendar: Calendar): String {
      val dateFormat = SimpleDateFormat("HH:mm", Locale.FRANCE)
      return dateFormat.format(calendar.time)
    }
  }


}