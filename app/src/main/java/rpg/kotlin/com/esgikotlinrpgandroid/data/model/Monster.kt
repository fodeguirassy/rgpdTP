package data.model

data class Monster (val type : MonsterType, var healthPoint : Int, val item : Item? = null)

enum class MonsterType (val typeName : String){
    ORC("Orc"),
    TROLL("Troll"),
    GOBLIN("Goblin"),
    DRAGON("Dragon"),
}
