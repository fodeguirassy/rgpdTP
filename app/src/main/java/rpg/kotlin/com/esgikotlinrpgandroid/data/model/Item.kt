package data.model

import rpg.kotlin.com.esgikotlinrpgandroid.R

data class Item(val type: ItemType)

enum class ItemType(val imgResource: Int? = null) {
    HEALTH_POTION(R.drawable.ic_health_potion_master),
    GRENADE,
    KEY(R.drawable.ic_key_master),
}