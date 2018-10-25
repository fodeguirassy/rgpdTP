package rpg.kotlin.com.esgikotlinrpgandroid.data.model

import java.util.*

/**
 * Created on 22/10/2018 by cyrilicard
 *
 */
data class Message(var user: UserType = UserType.GAME_MASTER, var message: String, var time: Calendar = Calendar.getInstance())

enum class UserType {
  GAME_MASTER,
  PLAYER
}
