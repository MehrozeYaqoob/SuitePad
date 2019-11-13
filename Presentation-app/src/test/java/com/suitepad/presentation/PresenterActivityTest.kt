package com.suitepad.presentation

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Created by Mehroze on 11/13/2019.
 */
@RunWith(PowerMockRunner::class)
class PresenterActivityTest {

    @Mock
    lateinit var activity: PresenterActivity

    @Before
    fun setUp() {
    }

    @Test
    fun sendBroadcastToHttpProxy() {
        activity.sendBroadcastToHttpProxy()
    }
}