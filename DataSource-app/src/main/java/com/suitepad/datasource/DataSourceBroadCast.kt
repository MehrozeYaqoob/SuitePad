package com.suitepad.datasource

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Mehroze on 11/11/2019.
 */
class DataSourceBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val jobIntent = Intent(context, DataSourceService::class.java)
        DataSourceService.enqueueWork(context!!,jobIntent)
    }
}