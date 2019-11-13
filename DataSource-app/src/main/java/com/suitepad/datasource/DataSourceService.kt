package com.suitepad.datasource

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_COMING_FROM
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_RESULT
import com.suitepad.constants_app.SuitePadConstants.Companion.PROXY_BR_CLASS
import com.suitepad.constants_app.SuitePadConstants.Companion.PROXY_PACKAGE
import com.suitepad.constants_app.SuitePadConstants.Companion.VAL_COMING_FROM_2
import com.suitepad.datasource.db.entity.MenuItemEntity
import com.suitepad.datasource.repository.DataSourceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


/**
 * Created by Mehroze on 11/11/2019.
 */
class DataSourceService : JobIntentService() {


    companion object {
        lateinit var dataSourceRepository: DataSourceRepository
        private const val JOB_ID = 1
        fun enqueueWork(context: Context, intent: Intent) {
            dataSourceRepository = DataSourceRepository(context.applicationContext)
            enqueueWork(context, DataSourceService::class.java!!, JOB_ID, intent)
        }
    }
    override fun onHandleWork(intent: Intent) {
        fetchMenuItemsFromDB()
    }

    /**
     * This will fetch data from DB and send broadcast to Proxy server
     */
    @SuppressLint("CheckResult")
    fun fetchMenuItemsFromDB() {
        dataSourceRepository.fetchMenuItemsFromDB().observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(Consumer {

                val listType = object : TypeToken<List<MenuItemEntity>>() {
                }.type
                val gson = Gson()
                val json = gson.toJson(it, listType)

                val intent = Intent()
                intent.component = ComponentName(PROXY_PACKAGE, PROXY_BR_CLASS)
                intent.putExtra(KEY_RESULT, json)
                intent.putExtra(KEY_COMING_FROM,VAL_COMING_FROM_2)
                sendBroadcast(intent)
            })
    }
}