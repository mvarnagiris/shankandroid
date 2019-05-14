package memoizrlabs.com.shankandroid

import android.view.View
import android.widget.EditText
import androidx.test.espresso.action.ViewActions

fun View.click() =matchThis.perform(ViewActions.click())
fun View.clickIME() =matchThis.perform(ViewActions.pressImeActionButton())

fun View.swipeUpUntil(view: View) {
    val maxAttempts = 10
    fun go(attempt: Int) {
        matchThis.perform(ViewActions.swipeUp())
        if (!view.isVisible() && attempt < maxAttempts) {
            go(attempt.inc())
        }
    }
    go(0)
}

fun View.swipeDown() {
    matchThis.perform(ViewActions.swipeDown())
}

infix fun EditText.enterText(text: String) = matchThis.perform(ViewActions.click(), ViewActions.typeText(text))
fun EditText.clearText() = matchThis.perform(ViewActions.clearText())

fun View.isVisible(): Boolean {
    val displayHeight = currentActivity.resources.displayMetrics.heightPixels
    val xy = intArrayOf(0, 0)

    getLocationOnScreen(xy)

    val top = xy[1]
    val bottom = top + height

    return top >= 0 && bottom < displayHeight
}

