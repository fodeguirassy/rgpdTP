package data.model

data class Room(
        val name: RoomName,
        var hasLockDoor: Boolean = false,
        var northRoom: Room? = null,
        var eastRoom: Room? = null,
        var southRoom: Room? = null,
        var westRoom: Room? = null,
        val monster: Monster? = null,
        val item: Item? = null
)

enum class RoomName(val roomName: String) {
    STARTING_ROOM("Starting room"),
    ROOM_1("Room 1"),
    ROOM_2("Room 2"),
    ROOM_3("Room 3"),
    ROOM_4("Room 4"),
    ENDING_ROOM("Ending room")
}