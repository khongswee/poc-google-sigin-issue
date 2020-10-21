package com.poc.googlesignin

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class SCBWebView : WebView {

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun displayUrl(url: String?) {
        url?.apply {
            loadUrl(this)
        }
    }

    fun displayData(data: String?) {
        data?.apply {
            loadData(this, MIME_TYPE, ENCODING)
        }
    }

    fun setAdvanceWebView(webViewConfig: SCBWebViewConfig) {
        webChromeClient = WebChromeClient()
        settings.apply {
            javaScriptEnabled = webViewConfig.isJavaScriptEnabled
            domStorageEnabled = webViewConfig.isDomStorageEnabled()
            builtInZoomControls = webViewConfig.isBuiltInZoomControls()
            displayZoomControls = webViewConfig.isDisplayZoomControls()

            webViewConfig.getZoomLevel()?.also {
                textZoom = it
            }
        }

        webViewConfig.isClearCache()?.also {
            clearCache(it)
            clearHistory()
        }
    }

    fun setJavascriptInterface(javaScriptCallbacks: JavaScriptCallbacks, javaScripName: String) {
        addJavascriptInterface(WebJavaScripInterface(javaScriptCallbacks), javaScripName)
    }

    fun executeJavaScript(webViewScriptConfig: WebViewScriptConfig, data: String = "") {
        post {
            val script = data.isEmpty().then { webViewScriptConfig.getScript() }
                    ?: webViewScriptConfig.getScript(data)
            evaluateJavascript(script, null)
        }
    }

    fun setWebViewBackgroundColor(@ColorRes color: Int) {
        setBackgroundColor(ContextCompat.getColor(context, color))
    }

    companion object {
        const val BASE_URL = "file:///android_asset/"
        const val MIME_TYPE = "text/html"
        const val ENCODING = "utf-8"
    }
}

inline infix fun <T> Boolean.then(param: () -> T): T? = if (this) param() else null

