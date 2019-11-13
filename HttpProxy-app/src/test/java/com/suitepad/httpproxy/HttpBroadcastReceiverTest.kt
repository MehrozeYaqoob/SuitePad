package com.suitepad.httpproxy

import android.app.Application
import android.content.Intent
import com.suitepad.constants_app.SuitePadConstants
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

/**
 * Created by Mehroze on 11/13/2019.
 */
@RunWith(MockitoJUnitRunner::class)
class HttpBroadcastReceiverTest {

    private var httpBroadcastReceiver : HttpBroadcastReceiver? = null

    @Mock
    lateinit var application: Application

    @Before
    fun setUp() {
        httpBroadcastReceiver = HttpBroadcastReceiver()

    }

    @Test
    fun onReceive_NullIntent() {
        httpBroadcastReceiver?.onReceive(application,null)
    }

    @Test
    fun onReceive_FromPresenter() {
        val intent = Intent()
        intent.putExtra(SuitePadConstants.KEY_COMING_FROM, SuitePadConstants.VAL_COMING_FROM_1)
        httpBroadcastReceiver?.onReceive(application,intent)
    }

    @Test
    fun onReceive_FromDataSource() {
        val intent = Intent()
        intent.putExtra(SuitePadConstants.KEY_COMING_FROM, SuitePadConstants.VAL_COMING_FROM_2)
        httpBroadcastReceiver?.onReceive(application,intent)
    }
}