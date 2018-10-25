package rpg.kotlin.com.esgikotlinrpgandroid.module.common

/**
 * Created on 21/10/2018 by cyrilicard
 *
 */
open class BasePresenter {
  open fun onCreated() = Unit
  open fun onResume() = Unit
  open fun onPause() = Unit
  open fun onStop() = Unit
  open fun onDestroy() = Unit
}