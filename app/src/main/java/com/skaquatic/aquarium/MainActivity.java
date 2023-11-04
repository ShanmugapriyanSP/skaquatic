package com.skaquatic.aquarium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private WebView mywebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("web_app")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });

        mywebView = findViewById(R.id.webview);
        WebSettings webSettings = mywebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUserAgentString(System.getProperty("http.agent"));

        mywebView.setWebViewClient(new MyWebClient()); // Set the custom WebViewClient

        mywebView.loadUrl("https://skaquatic.com/");
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (mywebView.canGoBack()) {
            mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
