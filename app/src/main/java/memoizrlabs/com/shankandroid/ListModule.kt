package memoizrlabs.com.shankandroid

import life.shank.ShankModule
import life.shank.scoped
import memoizrlabs.com.shankandroid.autoattachandroid.detachAware

object ListModule : ShankModule {
    val listPresenter = scoped { data: Int -> ListPresenter(data) }.detachAware()
    val otherListPresenter = scoped { data: Int -> OtherListPresenter(data) }.detachAware()
}