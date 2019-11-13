package com.suitepad.datasource.repository

import android.app.Application
import com.suitepad.datasource.api.MenuItemsAPI
import com.suitepad.datasource.db.SuitePadDatabase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.powermock.modules.junit4.PowerMockRunner
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(PowerMockRunner::class)
class DataSourceRepositoryTest {

    @Mock
    lateinit var api: MenuItemsAPI
    lateinit var repository: DataSourceRepository

    @Mock
    lateinit var application: Application

    @Before
    fun setUp() {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(application.getDatabasePath(SuitePadDatabase.DATABASE_NAME)).thenReturn(file)
        Mockito.`when`(file.exists()).thenReturn(true)
        repository = DataSourceRepository(application, api)
    }

    @Test
    fun fetchDataFromServer() {
        Mockito.`when`(repository.fetchDataFromServer()).thenReturn(Single.just(listOf()))
        repository.fetchDataFromServer().test().assertValue(listOf())
    }

}