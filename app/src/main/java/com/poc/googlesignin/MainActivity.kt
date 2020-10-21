package com.poc.googlesignin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.poc.googlesignin.WebJavaScripInterface.Companion.TRIGGER_NATIVE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), JavaScriptCallbacks, SCBWebViewConfig {
    companion object {
        private const val GOOGLE_SIGN_IN = 4432
        private const val UNIDENTIFIED_ERROR = "unidentifiedError"
    }

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadWeb()
    }

    private fun loadWeb() {
        wvContent.also {
            it.settings.javaScriptEnabled = true
            it.settings.domStorageEnabled = true
            it.loadUrl("file:///android_asset/www/index.html")
            it.addJavascriptInterface(WebJavaScripInterface(this), "WebviewJS")
//            it.setAdvanceWebView(this)
//            it.displayUrl("file:///android_asset/www/index.html")
//            it.setJavascriptInterface(this, "WebviewJS");
        }
    }

    override fun handleJavaScripInterface(bridgeKey: String, value: String) {
        when (bridgeKey) {
            TRIGGER_NATIVE -> {
                onClickGoogleSignIn()
            }
        }
    }

    private fun onClickGoogleSignIn() {
        val scopes = arrayOf(
            Scope("https://www.googleapis.com/auth/userinfo.profile"),
            Scope("https://www.googleapis.com/auth/business.manage"),
            Scope("https://www.googleapis.com/auth/plus.business.manage")
        )
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getResources().getString(R.string.default_web_client_id))
                .requestServerAuthCode(
                    getResources().getString(R.string.default_web_client_id),
                    true
                )
                .requestScopes(
                    Scope(Scopes.EMAIL),scopes[0],scopes[1],scopes[2]
                )
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient?.signInIntent

        startActivityForResult(
            signInIntent,
            GOOGLE_SIGN_IN
        )
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = try {
                task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
                sendInformationToWeb("${e.statusCode}")
                null
            }
            account?.let {
                sendInformationToWeb(it.email)
            }
        }
    }

    private fun sendInformationToWeb(information: String?) {
        wvContent.apply {
            post {
                evaluateJavascript("javascript:WebJS.onTriggerWeb('${information}')", null)
            }
        }
    }

    override val isJavaScriptEnabled: Boolean
        get() = true
}