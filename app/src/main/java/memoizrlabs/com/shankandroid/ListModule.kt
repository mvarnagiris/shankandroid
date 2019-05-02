package memoizrlabs.com.shankandroid

import life.shank.ShankModule
import life.shank.android.detachAware
import life.shank.scoped

object ListModule : ShankModule {
    val listPresenter = scoped { data: Int -> ListPresenter(data) }.detachAware()
    val otherListPresenter = scoped { data: Int -> OtherListPresenter(data) }.detachAware()
}
