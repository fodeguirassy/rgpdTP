package rpg.kotlin.com.esgikotlinrpgandroid.module.game.room.current

import android.support.v4.content.ContextCompat
import android.view.MenuItem
import data.model.Room
import kotlinx.android.synthetic.main.activity_current_room.current_room_east_tv
import kotlinx.android.synthetic.main.activity_current_room.current_room_locked_img
import kotlinx.android.synthetic.main.activity_current_room.current_room_monster_img
import kotlinx.android.synthetic.main.activity_current_room.current_room_north_tv
import kotlinx.android.synthetic.main.activity_current_room.current_room_south_tv
import kotlinx.android.synthetic.main.activity_current_room.current_room_west_tv
import rpg.kotlin.com.esgikotlinrpgandroid.R
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BaseActivity

class CurrentRoomActivity : BaseActivity(layoutRes = R.layout.activity_current_room), CurrentRoomInterface {

    override fun getPresenter() = CurrentRoomPresenter(this)

    override fun onCreated() {
        super.onCreated()
        this.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun setCurrentRoom(room: Room) {
        supportActionBar?.let {
            title = room.name.roomName
        }

        with(current_room_locked_img) {
            when (room.hasLockDoor) {
                false -> this.setImageResource(R.drawable.ic_unlocked)
            }
        }

        if (room.northRoom != null) {
            current_room_north_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.drawable.ic_double_door_opened))
        }
        if (room.eastRoom != null) {
            current_room_east_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, R.drawable.ic_double_door_opened_left), null)
        }
        if (room.westRoom != null) {
            current_room_west_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_double_door_opened_right), null, null, null)
        }
        if (room.southRoom != null) {
            current_room_south_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.ic_double_door_opened_bottom), null, null)
        }

        when {
            room.item != null -> {
                room.item.type.imgResource?.let {
                    current_room_monster_img.setImageResource(it)
                }
            }
            room.monster != null -> {
                room.monster.type.imgResource?.let {
                    current_room_monster_img.setImageResource(it)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let { menuItem ->
            return when (menuItem.itemId) {
                android.R.id.home -> {
                    getPresenter().onBackPressed()
                    true
                }
                else -> {
                    super.onOptionsItemSelected(item)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
