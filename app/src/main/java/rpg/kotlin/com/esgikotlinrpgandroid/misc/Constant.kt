package rpg.kotlin.com.esgikotlinrpgandroid.misc

import data.model.Room
import data.model.RoomName
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message

/**
 * Created on 25/10/2018 by cyrilicard
 *
 */
object Constant {
    val YES = listOf("o", "y", "oui", "yes", "yep", "ok", "yeah", "ye")
    val NO = listOf("n", "no", "non", "nope", "nan")
    const val QUIT = "q"
    var messages = mutableListOf<Message>()
    var currentRoom = Room(RoomName.STARTING_ROOM)
    var dungeonReady = false
}