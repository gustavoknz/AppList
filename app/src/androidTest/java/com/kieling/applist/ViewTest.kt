package com.kieling.applist

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kieling.applist.db.ApplistDao
import com.kieling.applist.model.AppApplist
import com.kieling.applist.view.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@LargeTest
@RunWith(AndroidJUnit4::class)
class ViewTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)
    private val daoMock: ApplistDao = Mockito.mock(ApplistDao::class.java)
    private val liveDataApps: MutableLiveData<List<AppApplist>> by lazy { MutableLiveData<List<AppApplist>>() }
    private val validAppList = listOf(
        AppApplist(1, "First app", 1F, "https://img.icons8.com/color/48/000000/image.png"),
        AppApplist(2, "Second app", 2F, "https://img.icons8.com/cotton/64/000000/image--v2.png"),
        AppApplist(3, "Third app", 3F, "https://img.icons8.com/officel/16/000000/edit-image.png")
    )

    @Test
    fun emptyList_ViewTest() {
        liveDataApps.value = validAppList
        Mockito.`when`(daoMock.getAll()).thenReturn(liveDataApps)
        onView(allOf(withId(R.id.list_recycler_view), isDisplayed())).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.list_recycler_view), isDisplayed())).check(matches(isDisplayed()))
    }
}