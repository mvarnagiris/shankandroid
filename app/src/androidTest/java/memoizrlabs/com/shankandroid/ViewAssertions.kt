package memoizrlabs.com.shankandroid

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.LayoutAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.google.android.material.textfield.TextInputLayout
import junit.framework.Assert
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun View.assertIsVisible() =
        matchThis.check(ViewAssertions.matches(isDisplayed()))
fun View.hasNoOverlaps() = matchThis.check(LayoutAssertions.noOverlaps())
fun View.assertIsNotVisible() = matchThis.check(ViewAssertions.matches(not(isDisplayed())))
val View.matchThis get() = onView(ViewMatchers.withId(this.id))
//val View.matchWithId get() = withId(this.id)

inline fun <reified T: Activity> assertCurrentActivity() = Assert.assertEquals(T::class.java, currentActivity.javaClass)
infix fun TextView.hasText(expected: String) = matchThis.check(ViewAssertions.matches(withText(expected)))
infix fun TextInputLayout.hasText(expected: String) = matchThis.check(ViewAssertions.matches(hasTextInputLayoutErrorText(expected)))

fun hasTextInputLayoutErrorText(expectedErrorText: String): Matcher<View> {
    return object : TypeSafeMatcher<View>() {

        override fun matchesSafely(view: View): Boolean {
            if (view !is TextInputLayout) {
                return false
            }
            val error = view.error ?: return false
            return expectedErrorText == error.toString()
        }

        override fun describeTo(description: Description) {}
    }
}