package com.kieling.applist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kieling.applist.TestCoroutineRule
import com.kieling.applist.db.ApplistDao
import com.kieling.applist.model.*
import com.kieling.applist.network.ApplistApiService
import com.kieling.applist.repository.ApplistRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AppListVewModelTest {
    private val daoMock: ApplistDao = mock(ApplistDao::class.java)
    private val apiMock: ApplistApiService = mock(ApplistApiService::class.java)
    private val liveDataApps: MutableLiveData<List<AppApplist>> by lazy { MutableLiveData<List<AppApplist>>() }

    private val emptyAppList = emptyList<AppApplist>()
    private val validAppList = listOf(
        AppApplist(1, "First app", 1F, "https://img.icons8.com/color/48/000000/image.png"),
        AppApplist(2, "Second app", 2F, "https://img.icons8.com/cotton/64/000000/image--v2.png"),
        AppApplist(3, "Third app", 3F, "https://img.icons8.com/officel/16/000000/edit-image.png")
    )

    @Mock
    private lateinit var appsObserver: Observer<List<AppApplist>>

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    @ExperimentalCoroutinesApi
    val testCoroutineRule = TestCoroutineRule()

    @Test
    @ExperimentalCoroutinesApi
    fun whenServerIsUnreachable_ViewModelListIsEmpty() {
        testCoroutineRule.runBlockingTest {
            liveDataApps.value = emptyAppList
            `when`(daoMock.getAll()).thenReturn(liveDataApps)
            val repository = ApplistRepository(daoMock, apiMock)
            val viewModel = AppListViewModel(repository)
            viewModel.appList.observeForever(appsObserver)
            verify(appsObserver).onChanged(emptyAppList)
            assertEquals(0, viewModel.appList.value!!.size)
            viewModel.dataState.value?.let { assertFalse(it) }
            viewModel.appList.removeObserver(appsObserver)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun whenServerIsReachable_ViewModelListIsEmpty() {
        testCoroutineRule.runBlockingTest {
            liveDataApps.value = emptyAppList
            `when`(daoMock.getAll()).thenReturn(liveDataApps)
            val repository = ApplistRepository(daoMock, apiMock)
            val viewModel = AppListViewModel(repository)
            viewModel.appList.observeForever(appsObserver)
            verify(appsObserver).onChanged(emptyAppList)
            assertEquals(0, viewModel.appList.value!!.size)
            viewModel.dataState.value?.let { assertTrue(it) }

            liveDataApps.value = validAppList
            verify(appsObserver).onChanged(validAppList)
            assertEquals(3, viewModel.appList.value!!.size)
            viewModel.dataState.value?.let { assertTrue(it) }

            viewModel.appList.removeObserver(appsObserver)
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun whenServerIsReachable_ViewModelListHasItems() {
        testCoroutineRule.runBlockingTest {
            liveDataApps.value = validAppList
            `when`(daoMock.getAll()).thenReturn(liveDataApps)
            val repository = ApplistRepository(daoMock, apiMock)
            val viewModel = AppListViewModel(repository)
            viewModel.appList.observeForever(appsObserver)
            verify(appsObserver).onChanged(validAppList)
            viewModel.dataState.value?.let { assertTrue(it) }
            assertEquals(3, viewModel.appList.value!!.size)
            viewModel.appList.removeObserver(appsObserver)
        }
    }
}