package module

import data.DataProvider
import data.model.CardinalDirection
import data.model.Item
import data.model.ItemType
import data.model.Monster
import data.model.Player
import data.model.Room
import data.model.RoomName
import data.model.Weapon
import data.model.exception.WeaponException
import rpg.kotlin.com.esgikotlinrpgandroid.R
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.UserType
import rpg.kotlin.com.esgikotlinrpgandroid.misc.Constant
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BasePresenter
import rpg.kotlin.com.esgikotlinrpgandroid.module.game.GameActivity

class GamePresenter(private val view: GameInterface) : BasePresenter {

    private lateinit var pseudo: String
    private var currentWeapon: Weapon? = null

    private lateinit var player: Player
    private var dungeon = DataProvider.initDungeon()

    private lateinit var previousRoom: Room
    private var currentRoom: Room? = null

    private var leaveTheGame = false
    private var winTheGame = false
    private var launchDungeon = false

    private lateinit var currentStoryLine: StoryLine
    private var headingToRoom = false
    private lateinit var currentPlayerAnswer: String

    override fun onResume() {
        super.onResume()

        Constant.messages.apply {
            if (this.count() > 0) {
                this.forEach {
                    if (it.user == UserType.PLAYER) {
                        view.addPlayerMessage(it)
                    } else {
                        view.displayMessage(it)
                    }
                }
            } else {
                currentStoryLine = StoryLine.storyLineFromIndex(0)
                manageStoryLine()
            }
        }
    }

    private fun addMessage(message: Message) {
        if (!Constant.dungeonReady) {
            Constant.messages.add(message)
        }
    }

    override fun onDestroy() {
        clearPreferences()
        super.onDestroy()
    }

    fun onMapClicked() {
        if (Constant.dungeonReady) {
            headingToRoom = true
            wantToLaunchDungeon()
            view.displayGoToRoom(currentRoom!!.name)
        } else {
            view.notifyDungeonNotReady()
        }
    }

    fun clearPreferences() {
        if (!headingToRoom) {
            view.clearMessages()
        }
    }

    fun onValidateEntryClick(entry: String) {
        if (entry.isNotBlank()) {
            currentPlayerAnswer = entry
            val playerMessage = Message(UserType.PLAYER, currentPlayerAnswer)
            view.addPlayerMessage(playerMessage)
            addMessage(playerMessage)
            triggerStoryLineAction()
        }
    }

    private fun manageStoryLine() {
        when (currentStoryLine) {
            StoryLine.WELCOME_TO_THE_GAME -> {
                view.displayWelcomeMessage()
                val welcomeMessage = (view as GameActivity).resources.getString(R.string.welcome_message)
                addMessage(Message(UserType.GAME_MASTER, welcomeMessage))
            }
            StoryLine.PLAYER_PSEUDO -> {
                view.displayPlayerPseudoReaction(pseudo)
                val reaction = (view as GameActivity).getString(R.string.funny_player_name, pseudo)
                addMessage(Message(UserType.GAME_MASTER, reaction))
                triggerStoryLineAction()
            }
            StoryLine.START_QUEST -> {
                view.displayStartQuestMessage()
                val question = (view as GameActivity).resources.getString(R.string.come_for_quest)
                addMessage(Message(UserType.GAME_MASTER, question))

            }
            StoryLine.DUNGEON_INFORMATION -> {
                view.displayDungeonInformation(dungeon.name)
                triggerStoryLineAction()

                val welcomeToDungeon = (view as GameActivity).resources.getString(R.string.first_foot_in_dungeon, dungeon.name)
                addMessage(Message(UserType.GAME_MASTER, welcomeToDungeon))
                Constant.dungeonReady = true
            }
            StoryLine.CHOOSE_WEAPON -> {
                view.displayNextCourse()
                //view.choosePlayerWeaponInformation(Weapon.values())
            }
        }
    }

    private fun triggerStoryLineAction() {
        when (currentStoryLine) {
            StoryLine.WELCOME_TO_THE_GAME -> {
                pseudo = currentPlayerAnswer
                clearTextField()
                updateStoryLine()
            }
            StoryLine.PLAYER_PSEUDO -> {
                updateStoryLine()
            }
            StoryLine.START_QUEST -> {
                prepareStarQuest()
            }
            StoryLine.DUNGEON_INFORMATION -> {
                updateStoryLine()
            }
            StoryLine.CHOOSE_WEAPON -> {
            }
        }
        manageStoryLine()
    }

    private fun clearTextField() {
        currentPlayerAnswer = ""
    }

    private fun updateStoryLine() {
        currentStoryLine = StoryLine.getNextStoryLinePoint(currentStoryLine)
    }

    private fun prepareStarQuest() {
        when (currentPlayerAnswer) {
            in Constant.YES -> {
                view.displayStartQuestPositiveAnswer()
                prepareDungeon()
                updateStoryLine()
                val positiveAnswer = (view as GameActivity).resources.getString(R.string.start_quest_yes)
                addMessage(Message(UserType.GAME_MASTER, positiveAnswer))
            }
            in Constant.NO -> {
                view.displayStartQuestNegativeAnswer()
                val negativeAnswer = (view as GameActivity).resources.getString(R.string.start_quest_no)
                addMessage(Message(UserType.GAME_MASTER, negativeAnswer))
            }
            else -> {
                view.displayStartQuestBadAnswer()
                val badAnswer = (view as GameActivity).resources.getString(R.string.start_quest_bad_answer)
                addMessage(Message(UserType.GAME_MASTER, badAnswer))
            }
        }
    }

    private fun prepareDungeon() {
        dungeon = DataProvider.initDungeon()
        currentRoom = dungeon.rooms[RoomName.STARTING_ROOM]!!
    }

    private fun wantToLaunchDungeon() {
        currentRoom = dungeon.rooms[RoomName.STARTING_ROOM]!!
        currentRoom?.let {
            view.displayDungeonInformation(it.name.roomName)
            Constant.currentRoom = it
        }
        choosePlayerWeapon()
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

    enum class StoryLine {
        WELCOME_TO_THE_GAME,
        PLAYER_PSEUDO,
        START_QUEST,
        DUNGEON_INFORMATION,
        CHOOSE_WEAPON;

        companion object {
            fun getNextStoryLinePoint(point: StoryLine): StoryLine = StoryLine.values()[point.ordinal + 1]
            fun storyLineFromIndex(index: Int): StoryLine = StoryLine.values().first { it.ordinal == index }
        }
    }
}