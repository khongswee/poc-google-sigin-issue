package com.poc.googlesignin

import android.webkit.JavascriptInterface

interface SCBWebViewConfig {

    val isJavaScriptEnabled: Boolean

    @JavascriptInterface
    fun isDomStorageEnabled(): Boolean {
        return false
    }

    @JavascriptInterface
    fun isBuiltInZoomControls(): Boolean {
        return false
    }
    @JavascriptInterface
    fun isDisplayZoomControls(): Boolean {
        return false
    }
    @JavascriptInterface
    fun getZoomLevel(): Int? {
        return null
    }
    @JavascriptInterface
    fun isClearCache(): Boolean? {
        return null
    }
}
