package data

import data.model.*

object DataProvider {

    lateinit var dungeon: Dungeon

    fun initDungeon(): Dungeon {
        dungeon = Dungeon(rooms = prepareDungeonRooms())
        return dungeon
    }

    private fun prepareDungeonRooms(): Map<RoomName, Room> {
        val startingRoom = Room(RoomName.STARTING_ROOM)
        val potionItem = Item(ItemType.HEALTH_POTION)
        val room1 = Room(RoomName.ROOM_1, southRoom = startingRoom, item = potionItem)
        val keyItem = Item(ItemType.KEY)
        val room2 = Room(RoomName.ROOM_2, southRoom = room1, item = keyItem)
        val keyItem2 = Item(ItemType.KEY)
        val goblin = Monster(MonsterType.GOBLIN, 40, keyItem2)
        val room3 = Room(RoomName.ROOM_3, northRoom = room2, westRoom = room1, monster = goblin)
        val troll = Monster(MonsterType.TROLL, 10)
        val room4 = Room(RoomName.ROOM_4, hasLockDoor = true, eastRoom = room1, monster = troll)
        val dragon = Monster(MonsterType.DRAGON, 200)
        val endingRoom = Room(RoomName.ENDING_ROOM, hasLockDoor = true, southRoom = room2, monster = dragon)

        startingRoom.northRoom = room1
        room1.northRoom = room2
        room1.eastRoom = room3
        room1.westRoom = room4
        room2.eastRoom = room3
        room2.northRoom = endingRoom

        return mapOf(
                RoomName.STARTING_ROOM to startingRoom,
                RoomName.ROOM_1 to room1,
                RoomName.ROOM_2 to room2,
                RoomName.ROOM_3 to room3,
                RoomName.ROOM_4 to room4,
                RoomName.ENDING_ROOM to endingRoom
        )
    }

}