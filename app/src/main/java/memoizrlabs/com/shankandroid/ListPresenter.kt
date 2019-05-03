package memoizrlabs.com.shankandroid

class ListPresenter(val data: Int) : PresenterAdapter<ListPresenter.View>() {
    init {
        println("creating new list presenter: $data")
    }

    override fun onAttach(v: ListPresenter.View) {
        println("attaching, data; $data")
        v.setContent(data)
    }

    override fun onDetach(v: ListPresenter.View) {
        v.doOnDetach()
    }

    interface View : PresenterAdapter.View {
        fun setContent(data: Int)
        fun doOnDetach()
    }
}
class OtherListPresenter(val data: Int) : PresenterAdapter<OtherListPresenter.View>() {
    init {
        println("creating new list presenter: $data")
    }

    override fun onAttach(v: OtherListPresenter.View) {
        println("attaching other presenter, data; $data")
        v.somethingFromOtherPresenter()
    }

    override fun onDetach(v: OtherListPresenter.View) {
        println("detaching other presenter, data; $data")
    }

    interface View : PresenterAdapter.View {
        fun somethingFromOtherPresenter()
    }
}
