package com.example.cryptocurrency

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    val mActivityRule = ActivityTestRule(HomeActivity::class.java)
    @Rule get

    /**
     * Test to validate whether the title
     * is being displayed or not
     */
    @Test
    fun displayScreenTitle(){
        BaristaVisibilityAssertions.assertDisplayed("Cryptocurrency");
    }


    /**
     * Test to validate whether the list of
     * 'Cryptogarphic Assets' is being displayed or not
     */
    @Test
    fun displayListOfAssets(){
        Thread.sleep(4000)

        assertRecyclerViewItemCount(R.id.recyclerViewAssets, 100)
        onView(AllOf.allOf(withId(R.id.textViewAssetName), ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.recyclerViewAssets), 0))))
                .check(ViewAssertions.matches(ViewMatchers.withText("Bitcoin")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(AllOf.allOf(withId(R.id.textViewAssetCode), ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.recyclerViewAssets), 0))))
                .check(ViewAssertions.matches(ViewMatchers.withText("BTC")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    /**
     * Test to validate whether the MarketFragment(Asset Details)
     * is being displayed or not on the click of an item on
     * AssetFragment(AssetList)
     *
     */
    @Test
    fun intentOnClickOfAssetItem(){
        Thread.sleep(4000)

        onView(withId(R.id.recyclerViewAssets))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.textViewExchangeIdTitle)).check(ViewAssertions.matches(ViewMatchers.withText("Exchange ID")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }


    /**
     * A Utility method to verify the nth Child of a parent
     */
    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}