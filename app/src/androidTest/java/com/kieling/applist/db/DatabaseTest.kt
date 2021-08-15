package com.kieling.applist.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kieling.applist.model.AppApplist
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var dao: ApplistDao
    private lateinit var db: ApplistDatabase
    private val appList = listOf(
        AppApplist(1, "First app", 1F, "https://img.icons8.com/color/48/000000/image.png"),
        AppApplist(2, "Second app", 2F, "https://img.icons8.com/cotton/64/000000/image--v2.png"),
        AppApplist(3, "Third app", 3F, "https://img.icons8.com/officel/16/000000/edit-image.png"),
        AppApplist(4, "Forth app", 4F, "https://img.icons8.com/cotton/64/000000/image--v2.png")
    )

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ApplistDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.getApplistDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    @Throws(Exception::class)
    fun createAndRetrieveAppLists() {
        dao.insertAll(appList)
        assertEquals(dao.getAll().getOrAwaitValue().size, appList.size)
    }

    @Test
    @Throws(Exception::class)
    fun createAppListAndGetApp() {
        dao.insertAll(appList)
        assertEquals(appList[2], dao.getApp(3))
    }

    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}