package rpg.kotlin.com.esgikotlinrpgandroid.module.game

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import data.model.Room
import data.model.RoomName
import data.model.Weapon
import kotlinx.android.synthetic.main.activity_game.*
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
    game_validate_btn.setOnClickListener { presenter.onValidateEntryClick(game_profile_edt.text.toString()) }
  }

  private fun initAdapter() {
    adapter = GameAdapter(this)
    val linearLayoutManager = LinearLayoutManager(this)
    game_recycler_view.layoutManager = linearLayoutManager
    game_recycler_view.adapter = adapter
  }

  private fun displayMessage(message: Message) {
    Handler().postDelayed({ adapter.addMessage(message) }, 300)
  }

  override fun addPlayerMessage(playerMessage: Message) {
    hideKeyboard()
    displayMessage(playerMessage)
    game_profile_edt.text.clear()
    game_recycler_view.scrollToPosition(adapter.itemCount - 1)
  }

  //region * * * Override function * * *
  override fun displayWelcomeMessage() {
    //before
    println("Salut à toi !")
    println("Bienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")

    //after (bad version)
    //game_information_txv.setText("Salut à toi ! \nBienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")

    //after (good version)
    displayMessage(Message(message = getString(R.string.welcome_message)))
  }

  override fun displayPlayerPseudo(pseudo: String) {
    //before
    //print("Pseudo :")
    //val myPseudo: String = readLine() ?: "toto"
    //print("En voilà un drôle de nom $myPseudo")
    //presenter.savePlayerPseudo(myPseudo)

    //after
    displayMessage(Message(message = getString(R.string.funny_player_name, pseudo)))
  }

  override fun displayStartQuestMessage() {
    displayMessage(Message(message = getString(R.string.come_for_quest)))
    displayYesOrNoChoice()
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

  private fun displayYesOrNoChoice() {
    displayMessage(Message(message = getString(R.string.yes_or_no_choice)))
  }
  //endregion
}
