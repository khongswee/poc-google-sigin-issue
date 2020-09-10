package com.poc.googlesignin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.poc.googlesignin.WebJavaScripInterface.Companion.TRIGGER_NATIVE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),JavaScriptCallbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadWeb()
    }

    private fun loadWeb(){
        wvContent.also {
            it.settings.javaScriptEnabled = true
            it.settings.domStorageEnabled = true
            it.loadUrl("file:///android_asset/www/index.html")
            it.addJavascriptInterface(WebJavaScripInterface(this), "WebviewJS")
        }
    }

    override fun handleJavaScripInterface(bridgeKey: String, value: String) {
        when (bridgeKey) {
            TRIGGER_NATIVE -> {
                Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
            }
        }
    }
}
