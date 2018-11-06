package rpg.kotlin.com.esgikotlinrpgandroid.module.common

/**
 * Created on 21/10/2018 by cyrilicard
 *
 */
interface BasePresenter {
   fun onCreated() = Unit
   fun onResume() = Unit
   fun onPause() = Unit
   fun onStop() = Unit
   fun onDestroy() = Unit
}