package memoizrlabs.com.shankandroid

import life.shank.AttachListener
import life.shank.Attachable

abstract class Presenter<V : Presenter.View> {
    abstract fun onAttach(v: V)
    abstract fun onDetach(v: V)

    interface View
}

abstract class PresenterAdapter<V>: Presenter<V>(), AttachListener<V> where V: Attachable, V: Presenter.View {
    override fun onAttach(v: V) {
        onAttach(v)
    }

    override fun onDetach(v: V) {
        onDetach(v)
    }

    override fun onDispose() {
        println("disposing")
    }

    interface View : Presenter.View, Attachable
}
