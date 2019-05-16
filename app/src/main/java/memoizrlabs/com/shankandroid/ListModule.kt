package memoizrlabs.com.shankandroid

import life.shank.ShankModule
import life.shank.android.supportAutoAttach
import life.shank.new
import life.shank.scoped

object ListModule : ShankModule {
    val listPresenter = new { data: Int -> ListPresenter(data) }.supportAutoAttach()
    val otherListPresenter = scoped { data: Int -> OtherListPresenter(data) }.supportAutoAttach()
}
