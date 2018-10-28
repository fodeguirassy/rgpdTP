package module

import data.model.Room
import data.model.RoomName
import data.model.Weapon
import rpg.kotlin.com.esgikotlinrpgandroid.data.model.Message

interface GameInterface {
    //cours 3
    fun displayWelcomeMessage()
    fun displayPlayerPseudoReaction(pseudo: String)
    fun displayStartQuestMessage()
    fun displayDungeonInformation(dungeonName: String)

    //cours 4
    fun choosePlayerWeaponInformation(weapons: Array<Weapon>)
    fun displayWeaponGameMasterMessage(weaponName: String)
    fun displayPlayerAreIn()
    fun displayPossibleDirection(room: Room)
    fun displayGoToRoom(roomName: RoomName)
    fun displayHasLockDoorYouNeedKey()
    fun displayPlayerHasAKey()
    fun displayPlayerHasNoKey()
    fun displayKeyNotFound()
    fun displayByeByeMessage()
    fun displayCongratulationMessage()
    fun displayEmptyRoomMessage()
    fun displayContinueOrLeaveChoice()
    fun playerFoundSomething()
    fun playerFoundPotion()
    fun playerFoundGrenade()
    fun playerFoundKey()
    fun playerAddItemToInventory()
    fun askPlayerWantFighting(typeName: String)
    fun addPlayerMessage(playerMessage: Message)
    fun displayStartQuestPositiveAnswer()
    fun displayStartQuestNegativeAnswer()
    fun displayStartQuestBadAnswer()

    //temporary
    fun displayNextCourse()
    //fun displayHasLockDoorPlayerOption(player: Player)
}