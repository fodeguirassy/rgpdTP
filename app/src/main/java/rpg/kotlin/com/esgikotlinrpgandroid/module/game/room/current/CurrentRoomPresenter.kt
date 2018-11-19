package rpg.kotlin.com.esgikotlinrpgandroid.module.game.room.current

import android.support.v4.app.NavUtils
import data.DataProvider
import rpg.kotlin.com.esgikotlinrpgandroid.misc.Constant
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BasePresenter

class CurrentRoomPresenter(private val view: CurrentRoomInterface) : BasePresenter {

    override fun onCreated() {
        super.onCreated()
        val dungeon = DataProvider.initDungeon()
        val currentRoom = dungeon.rooms.values.single { it.name.roomName == Constant.currentRoom.name.roomName }
        view.setCurrentRoom(currentRoom)
    }

    fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(view as CurrentRoomActivity)
    }
}