package com.suitepad.httpproxy

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.suitepad.constants_app.SuitePadConstants.Companion.ACTION_SEND
import com.suitepad.constants_app.SuitePadConstants.Companion.DATASOURCE_BR_CLASS
import com.suitepad.constants_app.SuitePadConstants.Companion.DATASOURCE_PACKAGE
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_COMING_FROM
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_JSON
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_RESULT
import com.suitepad.constants_app.SuitePadConstants.Companion.VAL_COMING_FROM_1
import com.suitepad.constants_app.SuitePadConstants.Companion.VAL_COMING_FROM_2

/**
 * Created by Mehroze on 11/11/2019.
 */
class HttpBroadcastReceiver : BroadcastReceiver() {

    /**
     * This broadcast is a mediator between Datasource and Presenter so it will have two routes to handle.
     * @param context Context
     * @param intent Intent
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.extras
        when(bundle?.getString(KEY_COMING_FROM)){
            VAL_COMING_FROM_1 -> {sendBroadcastToDatasource(context)}
            VAL_COMING_FROM_2 -> {sendBroadcastToPresenterWithData(context,bundle)}
        }
    }

    /**
     * Send request to Datasource to provide data. Since its a Static and Explicit broadcast (Normal)
     * so we will have less restriction to tackle from Android
     * @param context Context?
     */
    private fun sendBroadcastToDatasource(context: Context?) {
        val intent = Intent()
        intent.component = ComponentName(DATASOURCE_PACKAGE, DATASOURCE_BR_CLASS)
        context?.sendBroadcast(intent)
    }

    /**
     * This will send Dynamic broadcast with data and only component with specified action can receive it.
     * In our case Presenter will. This requires Security Check
     * @param context Context?
     * @param bundle Bundle
     */
    private fun sendBroadcastToPresenterWithData(context: Context?,bundle: Bundle) {
        val intent = Intent(ACTION_SEND)
        intent.putExtra(KEY_JSON, bundle.getString(KEY_RESULT))
        context?.sendBroadcast(intent)
    }

}