package module

import data.DataProvider
import data.model.*
import data.model.exception.WeaponException
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.UserType
import rpg.kotlin.com.esgikotlinrpgandroid.misc.Constant
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BasePresenter

class GamePresenter(private val view: GameInterface) : BasePresenter() {

  private lateinit var pseudo: String
  private var currentWeapon: Weapon? = null

  private lateinit var player: Player
  private lateinit var dungeon: Dungeon

  private lateinit var previousRoom: Room
  private var currentRoom: Room? = null

  private var leaveTheGame = false
  private var winTheGame = false
  private var launchDungeon = false

  private lateinit var currentText: String

  private lateinit var currentStoryLine: StoryLine

  override fun onCreated() {
    super.onCreated()
    currentStoryLine = StoryLine.WELCOME_TO_THE_GAME
    manageStoryLine()
  }

  private fun manageStoryLine() {
    when (currentStoryLine) {
      StoryLine.WELCOME_TO_THE_GAME -> view.displayWelcomeMessage()
      StoryLine.PLAYER_PSEUDO -> view.displayPlayerPseudo(pseudo)
      StoryLine.START_QUEST -> view.displayStartQuestMessage()
      StoryLine.DUNGEON_INFORMATION -> {
      }
      StoryLine.WEAPON -> {
      }
    }
  }

  private fun triggerStoryLineAction() {
    when (currentStoryLine) {
      StoryLine.WELCOME_TO_THE_GAME -> {
        pseudo = currentText
        currentText = ""
        currentStoryLine = StoryLine.getNextStoryLinePoint(currentStoryLine)
        manageStoryLine()
      }
      StoryLine.PLAYER_PSEUDO -> {

      }
      StoryLine.START_QUEST -> {
      }
      StoryLine.DUNGEON_INFORMATION -> {
      }
      StoryLine.WEAPON -> {
      }
    }
  }


  fun savePlayerPseudo(myPseudo: String) {
    this.pseudo = myPseudo
    // askForStartingQuest()
  }

  fun wantToLaunchDungeon(launchDungeon: Boolean) {
    this.launchDungeon = launchDungeon
    if (launchDungeon) {
      dungeon = DataProvider.initDungeon()
      currentRoom = dungeon.rooms[RoomName.STARTING_ROOM]!!
      view.displayDungeonInformation(dungeon.name)
      choosePlayerWeapon()
    }
  }

  private fun choosePlayerWeapon() {
    view.choosePlayerWeaponInformation(Weapon.values())
  }

  fun playerChooseWeapon(weaponChoice: Int) {
    currentWeapon = Weapon.getById(weaponChoice)

    when (currentWeapon) {
      Weapon.NONE -> throw WeaponException("Le chiffre saisi ne fait pas partie de la liste des possiblités !")
      else -> {
        view.displayWeaponGameMasterMessage(currentWeapon!!.weaponName)
        initPlayer()
        startDungeon()
      }
    }
  }

  private fun initPlayer() {
    player = Player(
        pseudo = pseudo,
        healthPoint = 100,
        weapon = currentWeapon!!,
        items = mutableListOf()
    )
    view.displayPlayerAreIn()
  }

  private fun startDungeon() {
    while (!leaveTheGame && !winTheGame) {
      checkCurrentRoom()
      if (leaveTheGame || winTheGame) break
      askForThePossiblePath()
    }

    if (leaveTheGame) view.displayByeByeMessage() else view.displayCongratulationMessage()
  }

  private fun askForThePossiblePath() {
    currentRoom?.let { view.displayPossibleDirection(it) }
  }

  private fun checkCurrentRoom() {
    currentRoom?.let { nonNullRoom ->
      when {
        nonNullRoom.item != null -> playerFoundItem(nonNullRoom.item)
        nonNullRoom.monster != null -> askForAFight(nonNullRoom.monster)
        else -> view.displayEmptyRoomMessage()
      }
    }

    if (!leaveTheGame && !winTheGame) view.displayContinueOrLeaveChoice()
  }

  private fun playerFoundItem(item: Item) {
    view.playerFoundSomething()
    when (item.type) {
      ItemType.HEALTH_POTION -> {
        view.playerFoundPotion()
        player.nbPotion++
      }
      ItemType.GRENADE -> {
        view.playerFoundGrenade()
        player.nbGrenade++
      }
      ItemType.KEY -> {
        view.playerFoundKey()
        player.nbKey++
      }
    }
    player.items.add(item)
    view.playerAddItemToInventory()
  }

  private fun askForAFight(monster: Monster) {
    view.askPlayerWantFighting(monster.type.typeName)
  }

  fun directionChoice(direction: String?) {
    direction?.let { nonNullDirection ->
      previousRoom = currentRoom!!.copy()

      currentRoom = when (nonNullDirection.toUpperCase()) {
        CardinalDirection.NORTH.letter -> currentRoom?.northRoom!!
        CardinalDirection.EAST.letter -> currentRoom?.eastRoom!!
        CardinalDirection.SOUTH.letter -> currentRoom?.southRoom!!
        CardinalDirection.WEST.letter -> currentRoom?.westRoom!!
        else -> {
          null
        }
      }

      currentRoom?.let { nonNullCurrentRoom ->
        view.displayGoToRoom(nonNullCurrentRoom.name)
        if (nonNullCurrentRoom.hasLockDoor) {
          view.displayHasLockDoorYouNeedKey()
          if (player.nbKey > 0) {
            view.displayPlayerHasAKey()
            removeKeyToPlayer()
          } else {
            view.displayPlayerHasNoKey()
            playerHasNoKey()
          }
        }
      }
    }
  }

  private fun removeKeyToPlayer() {
    val key = player.items.find { item -> item.type == ItemType.KEY }
    key?.let { nonNullKey ->
      player.nbKey--
      player.items.remove(nonNullKey)
    } ?: view.displayKeyNotFound()
  }

  private fun playerHasNoKey() {
    currentRoom = previousRoom
  }

  fun continueOrLeaveChoice(userChoice: String?) {
    userChoice?.let {
      if (it == Constant.QUIT) leaveTheGame = true
    }
  }

  fun onValidateEntryClick(entry: String) {
    if (entry.isNotBlank()) {
      currentText = entry
      val playerMessage = Message(UserType.PLAYER, currentText)
      view.addPlayerMessage(playerMessage)
      triggerStoryLineAction()
    }
  }

  enum class StoryLine {
    WELCOME_TO_THE_GAME,
    PLAYER_PSEUDO,
    START_QUEST,
    DUNGEON_INFORMATION,
    WEAPON;

    companion object {
      fun getNextStoryLinePoint(point: StoryLine): StoryLine = StoryLine.values()[point.ordinal + 1]
    }
  }

}