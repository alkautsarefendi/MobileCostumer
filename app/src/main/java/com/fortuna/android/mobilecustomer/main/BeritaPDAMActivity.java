package com.fortuna.android.mobilecustomer.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class BeritaPDAMActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog Pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.title_view, null);

        ((TextView) v.findViewById(R.id.titleAB)).setText(this.getTitle());
        getSupportActionBar().setCustomView(v);

        setContentView(R.layout.activity_berita_pdam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponent();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initComponent() {

        webView = findViewById(R.id.webView);

        webView.loadData("Loading ! ", "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);

        Pdialog = ProgressDialog.show(this, null, "Menampilkan Halaman...");

        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl("http://www.pdamtkr.co.id");

        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        webView.setVerticalScrollBarEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setOnKeyListener(new OnKeyListenerHandler());
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Pdialog.show();
            Pdialog.setIndeterminate(false);
            Pdialog.setCancelable(true);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Pdialog.show();
            Pdialog.setIndeterminate(false);
            Pdialog.setCancelable(true);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            if(Pdialog.isShowing()) {
                Pdialog.dismiss();
            }

        }
    }


    private class OnKeyListenerHandler implements View.OnKeyListener {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                WebView webView = (WebView) v;

                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        if (webView.canGoBack()) {
                            webView.goBack();
                            overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                            return true;
                        }
                        break;
                }
            }

            return false;
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
        }
    }


    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuback, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
