package rpg.kotlin.com.esgikotlinrpgandroid.module.game

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_game_master_message.view.game_master_message_date_txv
import kotlinx.android.synthetic.main.item_game_master_message.view.game_master_message_txv
import kotlinx.android.synthetic.main.item_player_message.view.player_message_time_txv
import kotlinx.android.synthetic.main.item_player_message.view.player_message_txv
import rpg.kotlin.com.esgikotlinrpgandroid.R
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.UserType
import rpg.kotlin.com.esgikotlinrpgandroid.misc.DateUtils
import rpg.kotlin.com.esgikotlinrpgandroid.misc.inflate
import java.util.ArrayList

/**
 * Created on 22/10/2018 by cyrilicard
 *
 */
internal class GameAdapter(private val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {


    private val messages: MutableList<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == QUESTION_ITEM) {
            GameMasterMessageViewHolder(parent.inflate(R.layout.item_game_master_message))
        } else {
            PlayerMessageViewHolder(parent.inflate(R.layout.item_player_message))
        }
    }

    private fun getUserType(position: Int) = messages[position].user

    override fun getItemViewType(position: Int): Int {
        return when (getUserType(position)) {
            UserType.GAME_MASTER -> QUESTION_ITEM
            UserType.PLAYER -> ANSWER_ITEM
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    fun addMessage(message: Message) {
        messages.add(message)
        notifyDataSetChanged()
    }

    fun clearMessages() {
        messages.clear()
        notifyDataSetChanged()
    }

    companion object {
        private const val QUESTION_ITEM = 0
        private const val ANSWER_ITEM = 1
    }

    inner class GameMasterMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageTxv: TextView = view.game_master_message_txv
        private var timeTxv: TextView = view.game_master_message_date_txv

        override fun bind(message: Message) {
            messageTxv.text = message.message
            timeTxv.text = DateUtils.calendarToStringTime(message.time)
        }
    }

    inner class PlayerMessageViewHolder(view: View) : MessageViewHolder(view) {
        private var messageTxv: TextView = view.player_message_txv
        private var timeTxv: TextView = view.player_message_time_txv

        override fun bind(message: Message) {
            messageTxv.text = message.message
            timeTxv.text = DateUtils.calendarToStringTime(message.time)
        }
    }

}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}