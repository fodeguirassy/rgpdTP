package rpg.kotlin.com.esgikotlinrpgandroid.module.game

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import data.model.Room
import data.model.RoomName
import data.model.Weapon
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.merge_header_game_view.*
import module.GameInterface
import module.GamePresenter
import rpg.kotlin.com.esgikotlinrpgandroid.R
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message
import rpg.kotlin.com.esgikotlinrpgandroid.misc.hideKeyboard
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BaseActivity

class GameActivity : BaseActivity(layoutRes = R.layout.activity_game), GameInterface {

  private val presenter = GamePresenter(this)

  private lateinit var adapter: GameAdapter

  override fun getPresenter() = presenter

  override fun onCreated() {
    initAdapter()
    super.onCreated()
    initListener()
  }

  private fun initListener() {

    game_recycler_view.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
      if (bottom < oldBottom) {
        game_recycler_view.smoothScrollToPosition(adapter.itemCount)
      }
    }

    game_validate_btn.setOnClickListener { presenter.onValidateEntryClick(game_profile_edt.text.toString()) }

    game_map_btn.setOnClickListener {
      presenter.onMapClick()
      Toast.makeText(this, "En construction! \nRome ne s'est pas faite en un jour!", Toast.LENGTH_SHORT).show()
    }
  }

  private fun initAdapter() {
    adapter = GameAdapter(this)
    val linearLayoutManager = LinearLayoutManager(this)
    game_recycler_view.layoutManager = linearLayoutManager
    game_recycler_view.adapter = adapter
  }

  private fun displayMessage(message: Message) {
    Handler().postDelayed({
      adapter.addMessage(message)
      game_recycler_view.smoothScrollToPosition(adapter.itemCount)
    }, 300)
  }

  override fun addPlayerMessage(playerMessage: Message) {
    hideKeyboard()
    displayMessage(playerMessage)
    game_profile_edt.text.clear()
    //game_recycler_view.scrollToPosition(adapter.itemCount - 1)
  }

  //region * * * Override function * * *
  /**
   * This method is just an example of how to transform step by step console code to android code
   */
  override fun displayWelcomeMessage() {
    //before
    println("Salut à toi !")
    println("Bienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")

    //after (bad version)
    //game_information_txv.setText("Salut à toi ! \nBienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")

    //after (good version)
    displayMessage(Message(message = getString(R.string.welcome_message)))
  }

  override fun displayPlayerPseudoReaction(pseudo: String) {
    displayMessage(Message(message = getString(R.string.funny_player_name, pseudo)))
  }

  override fun displayStartQuestMessage() {
    displayMessage(Message(message = getString(R.string.come_for_quest)))
  }

  override fun displayStartQuestPositiveAnswer() {
    displayMessage(Message(message = getString(R.string.start_quest_yes)))
  }

  override fun displayStartQuestNegativeAnswer() {
    displayMessage(Message(message = getString(R.string.start_quest_no)))
  }

  override fun displayStartQuestBadAnswer() {
    displayMessage(Message(message = getString(R.string.start_quest_bad_answer)))
  }

  override fun displayDungeonInformation(dungeonName: String) {
    displayMessage(Message(message = getString(R.string.first_foot_in_dungeon, dungeonName)))
  }

  override fun choosePlayerWeaponInformation(weapons: Array<Weapon>) {
  }

  override fun displayWeaponGameMasterMessage(weaponName: String) {
  }

  override fun displayPlayerAreIn() {
  }

  override fun displayPossibleDirection(room: Room) {
  }

  override fun displayGoToRoom(roomName: RoomName) {
  }

  override fun displayHasLockDoorYouNeedKey() {
  }

  override fun displayPlayerHasAKey() {
  }

  override fun displayPlayerHasNoKey() {
  }

  override fun displayKeyNotFound() {
  }

  override fun displayByeByeMessage() {
  }

  override fun displayCongratulationMessage() {
  }

  override fun displayEmptyRoomMessage() {
  }

  override fun displayContinueOrLeaveChoice() {
  }

  override fun playerFoundSomething() {
  }

  override fun playerFoundPotion() {
  }

  override fun playerFoundGrenade() {
  }

  override fun playerFoundKey() {
  }

  override fun playerAddItemToInventory() {
  }

  override fun askPlayerWantFighting(typeName: String) {
  }

  override fun displayNextCourse() {
    Toast.makeText(this, "La suite au prochain cours soldat ! \nAiguise ton arme en attendant ;)", Toast.LENGTH_SHORT).show()
  }
  //endregion


  private fun displayYesOrNoChoice() {
    displayMessage(Message(message = getString(R.string.yes_or_no_choice)))
  }
}
