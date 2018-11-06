package data.model

import rpg.kotlin.com.esgikotlinrpgandroid.R

data class Monster (val type : MonsterType, var healthPoint : Int, val item : Item? = null)

enum class MonsterType (val typeName : String, val imgResource : Int? = null) {
    ORC("Orc"),
    TROLL("Troll", R.drawable.ic_troll_master),
    GOBLIN("Goblin", R.drawable.ic_goblin_master),
    DRAGON("Dragon", R.drawable.ic_dragon_master),
}
