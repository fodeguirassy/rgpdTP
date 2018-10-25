package data.model

/**
 * Created by cyrilicard on 02/09/2018.
 */
data class Player(
  val pseudo: String,
  var healthPoint: Int,
  var weapon: Weapon,
  var items : MutableList<Item>,
  var nbPotion : Int = 0,
  var nbGrenade : Int = 0,
  var nbKey : Int = 0
)

/**
 * transform latter in sealed class
 */
enum class Weapon (val id : Int, val weaponName : String){
  DAGGER(1, "dague"),
  SWORD(2, "épée"),
  AXE(3, "hache"),
  BOW(4, "arc"),
  MAGIC_WAND(5, "baguette magique"),
  NONE(0, "");

  companion object {
    fun getById(id : Int) : Weapon {
      for (weapon in Weapon.values()) {
        if (weapon.id == id) return weapon
      }
      return NONE
    }
  }
}