package com.poc.googlesignin

import android.webkit.JavascriptInterface

interface WebViewScriptConfig {

    @JavascriptInterface
    fun javaScriptName(): String {
        return WEB_JS_SCRIPT_NAME
    }

    @JavascriptInterface
    fun functionName(): String {
        return WEB_JS_FUNCTION_NAME
    }

    @JavascriptInterface
    fun javaScriptData(): String {
        return ""
    }

    @JavascriptInterface
    fun getScript(data: String = ""): String =
        "javascript:${javaScriptName()}.${functionName()}('${data}')"

    @JavascriptInterface
    fun getScript(): String = getScript(javaScriptData())

    companion object {
        private const val WEB_JS_SCRIPT_NAME = "WebJS"
        private const val WEB_JS_FUNCTION_NAME = "WebviewJS"
    }
}
