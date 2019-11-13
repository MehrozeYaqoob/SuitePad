package com.suitepad.datasource

import android.app.Application
import android.content.Intent
import com.suitepad.datasource.api.MenuItemsAPI
import com.suitepad.datasource.db.SuitePadDatabase
import com.suitepad.datasource.repository.DataSourceRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.powermock.modules.junit4.PowerMockRunner
import java.io.File


/**
 * Created by Mehroze on 11/13/2019.
 */
@RunWith(PowerMockRunner::class)
class DataSourceBroadCastTest {

    private var dataSourceBroadCast : DataSourceBroadCast? = null
    lateinit var repository: DataSourceRepository
    @Mock
    lateinit var api: MenuItemsAPI

    @Mock
    lateinit var application: Application

    @Before
    fun setUp() {
        val file = Mockito.mock(File::class.java)
        Mockito.`when`(application.getDatabasePath(SuitePadDatabase.DATABASE_NAME)).thenReturn(file)
        Mockito.`when`(file.exists()).thenReturn(true)
        repository = DataSourceRepository(application, api)

        dataSourceBroadCast = DataSourceBroadCast()
    }

    @Test
    fun onReceive() {
        val intent = Intent(application,DataSourceService::class.java)
        dataSourceBroadCast?.onReceive(application,intent)
    }
}