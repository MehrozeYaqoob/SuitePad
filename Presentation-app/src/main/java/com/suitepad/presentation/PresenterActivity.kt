package com.suitepad.presentation

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.*
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.suitepad.constants_app.SuitePadConstants.Companion.ACTION_SEND
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_COMING_FROM
import com.suitepad.constants_app.SuitePadConstants.Companion.KEY_JSON
import com.suitepad.constants_app.SuitePadConstants.Companion.LOCAL_URL
import com.suitepad.constants_app.SuitePadConstants.Companion.PROXY_BR_CLASS
import com.suitepad.constants_app.SuitePadConstants.Companion.PROXY_PACKAGE
import com.suitepad.constants_app.SuitePadConstants.Companion.REMOTE_URL
import com.suitepad.constants_app.SuitePadConstants.Companion.VAL_COMING_FROM_1
import com.suitepad.presentation.databinding.ActivityMainBinding
import com.suitepad.presentation.mvvm.BaseActivityMVVM
import com.suitepad.presentation.viewmodel.PresenterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.UnsupportedEncodingException

/**
 * Created by Mehroze on 11/12/2019.
 */
class PresenterActivity: BaseActivityMVVM<ActivityMainBinding, PresenterViewModel>(){

    private var jsonData = ""
    private var inputStream: InputStream? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getBindingViewModel(): PresenterViewModel {
        return ViewModelProvider(this).get(PresenterViewModel::class.java)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowUniversalAccessFromFileURLs = true
        webView.webViewClient = webViewClient
        webView.loadUrl("file:///android_asset/suitepad_frontend.html")

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(receiver, IntentFilter(ACTION_SEND))
    }

    private val webViewClient = object : WebViewClient() {

        @TargetApi(Build.VERSION_CODES.M)
        override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {

            val uri = request.url
            return if (uri.toString().contains(REMOTE_URL)) {
                getMenuDataJsonResponse(uri)
            } else {
                super.shouldInterceptRequest(view, request) ?: processNullResponse(LOCAL_URL)
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            val builder = AlertDialog.Builder(this@PresenterActivity)
            var message = "SSL Certificate error."
            when (error.primaryError) {
                SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
                SslError.SSL_EXPIRED -> message = "The certificate has expired."
                SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
                SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
            }

            message += " Do you want to continue anyway?"

            builder.setTitle("SSL Certificate Error")
            builder.setMessage(message)
            builder.setPositiveButton("Proceed") { _, _ -> handler.proceed() }
            builder.setNegativeButton("Cancel") { _, _ -> handler.cancel() }
            val dialog = builder.create()
            dialog.show()
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(webView, url)
        }

        private fun getMenuDataJsonResponse(uri: Uri): WebResourceResponse {

            val thread = object : Thread() {
                override fun run() {
                    sendBroadcastToHttpProxy()
                }
            }
            thread.start()
            SystemClock.sleep(500)
            try {
                inputStream = ByteArrayInputStream(jsonData.toByteArray(charset("UTF-8")))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return WebResourceResponse("application/json; charset=UTF-8", "UTF-8", inputStream)
        }

        private fun processNullResponse(url: String): WebResourceResponse {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            return WebResourceResponse(
                response.header("content-type", "text/plain"),
                response.header("content-encoding", "utf-8"), response.body()!!.byteStream()
            )
        }
    }

    /**
     * This will send first request to Proxy server as broadcast
     */
    fun sendBroadcastToHttpProxy() {
        val intent = Intent()
        intent.component = ComponentName(PROXY_PACKAGE, PROXY_BR_CLASS)
        intent.putExtra(KEY_COMING_FROM,VAL_COMING_FROM_1)
        sendBroadcast(intent)
    }

    /**
     * Dynamic and Explicit Broadcast Receiver, Listening for an event triggered from Proxy server
     */
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(ACTION_SEND.equals(intent?.action)){
                intent?.let {  jsonData = it.getStringExtra(KEY_JSON) }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }
}
