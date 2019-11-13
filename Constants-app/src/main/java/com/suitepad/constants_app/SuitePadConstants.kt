package com.suitepad.constants_app

/**
 * Created by Mehroze on 11/12/2019.
 */
class SuitePadConstants {
    companion object {
        const val KEY_COMING_FROM = "comingFrom"
        const val VAL_COMING_FROM_1 = "fromPresenter"
        const val VAL_COMING_FROM_2 = "fromDataSource"
        const val KEY_RESULT = "result"
        const val KEY_JSON = "json"


        const val ACTION_SEND = "com.suitepad.presentation.ACTION_SEND"
        const val PROXY_PACKAGE = "com.suitepad.httpproxy"
        const val PROXY_BR_CLASS = "com.suitepad.httpproxy.HttpBroadcastReceiver"

        const val DATASOURCE_PACKAGE = "com.suitepad.datasource"
        const val DATASOURCE_BR_CLASS = "com.suitepad.datasource.DataSourceBroadCast"

        const val REMOTE_URL = "someremoteurl.com/sample.json"
        const val LOCAL_URL = "http://localhost:8080/file.json"

    }
}