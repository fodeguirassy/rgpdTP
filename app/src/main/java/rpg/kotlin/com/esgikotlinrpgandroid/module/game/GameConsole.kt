package module


//@Deprecated("for old school people :)")
/*
class GameConsole(private val presenter: GamePresenter) : GameInterface {
  override fun displayPlayerPseudoReaction(pseudo: String) {

  }

  override fun addPlayerMessage(playerMessage: Message) {
  }

  override fun displayWelcomeMessage() {
    println("Salut à toi !")
    println("Bienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")
  }

  override fun displayPlayerPseudoReaction() {
    print("Pseudo : ")
    val myPseudo: String = readLine() ?: "toto"
    print("En voilà un drôle de nom $myPseudo")
    presenter.savePlayerPseudo(myPseudo)
  }

  override fun displayStartQuestMessage() {
    print(", tu viens ici pour la quête ?\n")

    printYesOrNoChoice()

    var comeToStartQuest = readLine()
    var launchDungeon = false

    while (!launchDungeon) when {
      comeToStartQuest.equals(YES) -> {
        println("Je savais que tu avais l'ame d'un guerrier, je l'ai vu dès que tu t'es présenté à moi !")
        launchDungeon = true
      }
      comeToStartQuest.equals(NO) -> {
        //todo complete refused start request
      }
      else -> {
        println("Hum, Hum, ce n'est pas une bonne réponse")
        comeToStartQuest = readLine()
      }
    }
    presenter.wantToLaunchDungeon(launchDungeon)
  }

  override fun displayDungeonInformation(dungeonName: String) {
    Utils.waitingThread()
    displayHugeGameName()
    println("Vous venez de pénétrer dans le donjon de $dungeonName")
  }

  private fun displayHugeGameName() {
    val hugeTitle =
        "\n\n\n\n ___  __    ________  _________  ___       ___  ________           ________  ________  ________         ___  ________  ________      \n" +
            "|\\  \\|\\  \\ |\\   __  \\|\\___   ___\\\\  \\     |\\  \\|\\   ___  \\        |\\   ___ \\|\\   __  \\|\\   ___  \\      |\\  \\|\\   __  \\|\\   ___  \\    \n" +
            "\\ \\  \\/  /|\\ \\  \\|\\  \\|___ \\  \\_\\ \\  \\    \\ \\  \\ \\  \\\\ \\  \\       \\ \\  \\_|\\ \\ \\  \\|\\  \\ \\  \\\\ \\  \\     \\ \\  \\ \\  \\|\\  \\ \\  \\\\ \\  \\   \n" +
            " \\ \\   ___  \\ \\  \\\\\\  \\   \\ \\  \\ \\ \\  \\    \\ \\  \\ \\  \\\\ \\  \\       \\ \\  \\ \\\\ \\ \\  \\\\\\  \\ \\  \\\\ \\  \\  __ \\ \\  \\ \\  \\\\\\  \\ \\  \\\\ \\  \\  \n" +
            "  \\ \\  \\\\ \\  \\ \\  \\\\\\  \\   \\ \\  \\ \\ \\  \\____\\ \\  \\ \\  \\\\ \\  \\       \\ \\  \\_\\\\ \\ \\  \\\\\\  \\ \\  \\\\ \\  \\|\\  \\\\_\\  \\ \\  \\\\\\  \\ \\  \\\\ \\  \\ \n" +
            "   \\ \\__\\\\ \\__\\ \\_______\\   \\ \\__\\ \\ \\_______\\ \\__\\ \\__\\\\ \\__\\       \\ \\_______\\ \\_______\\ \\__\\\\ \\__\\ \\________\\ \\_______\\ \\__\\\\ \\__\\\n" +
            "    \\|__| \\|__|\\|_______|    \\|__|  \\|_______|\\|__|\\|__| \\|__|        \\|_______|\\|_______|\\|__| \\|__|\\|________|\\|_______|\\|__| \\|__|\n\n\n\n"
    println(hugeTitle)
  }

  override fun choosePlayerWeaponInformation(weapons: Array<Weapon>) {
    println("Avant toute chose tu vas devoir choisir une arme !")

    for (weapon: Weapon in weapons) {
      if (weapon != Weapon.NONE) println("'${weapon.id}' --> ${weapon.weaponName}")
    }

    println("Quel est ton choix ?")
    playerWeaponChoice()
  }

  private fun playerWeaponChoice() {
    val reader = Scanner(System.`in`)
    var correctWeaponChoice = false

    do try {
      presenter.playerChooseWeapon(reader.nextInt())
      correctWeaponChoice = true
    } catch (ex: InputMismatchException) {
      println("Attention à saisir un chiffre valide !")
      reader.next()
    } catch (ex: WeaponException) {
      println(ex.message)
      reader.reset()
    }
    while (!correctWeaponChoice)
  }

  override fun displayWeaponGameMasterMessage(weaponName: String) {
    println("Hum, je n'aurais jamais choisi une $weaponName\n")
  }

  override fun displayPlayerAreIn() {
    println("Te voilà rentré dans le donjon...")
  }

  override fun displayPossibleDirection(room: Room) {
    println("Tu peux choisir parmi ces options : ")

    room.northRoom?.run { println("  'N' --> North ?") }
    room.eastRoom?.run { println("  'E' --> East ?") }
    room.southRoom?.run { println("  'S' --> South ?") }
    room.westRoom?.run { println("  'W' --> West ?") }

    println("\nVers ou vas t'on ? ")

    presenter.directionChoice(readLine())
  }

  override fun displayHasLockDoorYouNeedKey() {
    println("Mince cette porte est fermé il faut une clef pour l'ouvrir !")
  }

  override fun displayPlayerHasAKey() {
    println("Par chance je vois que vous en avait une dans votre inventaire")
  }

  override fun displayPlayerHasNoKey() {
    println("Vous ne passerez pas !!! (Dumbledor)")
    println("Vous allez devoir trouver une clef pour continuer par ici !")
  }

  override fun displayKeyNotFound() {
    println("Vous n'avez aucune clé dans votre sac à dos !!")
  }

  override fun displayGoToRoom(roomName: RoomName) {

  }

  override fun displayByeByeMessage() {
    println(" _o/ au revoir !")
  }

  override fun displayCongratulationMessage() {
    println("C'est incroyable voici Excalibur !")
    println("\n\n         oxxxxx{=================>\n\n")
    println("Bravo! la partie est finie!")
  }

  override fun displayEmptyRoomMessage() {
    println("Malheuresement cette pièce est vide !")
  }

  override fun playerFoundSomething() {
    println("Vous avez trouvé quelque chose !")
  }

  override fun playerFoundPotion() {
    println("> Un potion de vie !")
  }

  override fun playerFoundGrenade() {
    println("> Une grenade prenez garde !")
  }

  override fun playerFoundKey() {
    println("> Une clef !")
  }

  override fun playerAddItemToInventory() {
    println("Hop dans l'inventaire")
  }


  override fun displayContinueOrLeaveChoice() {
    println("Que souhaitez vous faire ?")
    println("  'c' --> Continuer d'avancer")
    println("  'q' --> Quitter le donjon")
    presenter.continueOrLeaveChoice(readLine())
  }

  override fun askPlayerWantFighting(typeName: String) {
    // for cours 3
  }

  private fun printYesOrNoChoice() {
    println("  'o' --> Oui")
    println("  'n' --> Non")
  }

  companion object {
    const val YES = "o"
    const val NO = "n"
    const val QUIT = "q"
  }
}
*/