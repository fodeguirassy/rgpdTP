package data.model

data class Item ( val type : ItemType)


enum class ItemType {
    HEALTH_POTION,
    GRENADE,
    KEY,
}