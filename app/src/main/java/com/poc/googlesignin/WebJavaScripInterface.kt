package com.poc.googlesignin

import android.webkit.JavascriptInterface

class WebJavaScripInterface(private val callbacks: JavaScriptCallbacks) {
    @JavascriptInterface
    fun onAppScript(bridgeKey: String, value: String) {
        callbacks.handleJavaScripInterface(bridgeKey, value)
    }

    @JavascriptInterface
    fun triggerNative(toast: String) {
        callbacks.handleJavaScripInterface(TRIGGER_NATIVE, toast)
    }

    companion object {
        const val TRIGGER_NATIVE = "triggerNative"
    }
}

interface JavaScriptCallbacks {
    fun handleJavaScripInterface(bridgeKey: String, value: String)
}
