package com.fortuna.android.mobilecustomer.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.lokasi.LokasiKantorHasilActivity;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class LokasiKantorActivity extends AppCompatActivity {

    ImageButton buttonFacebook, buttonWeb, buttonEmail, buttonTwitter;
    Button buttonLokasi;
    private Bundle bundleAnimation;

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

        setContentView(R.layout.activity_lokasi_kantor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        initComponent();
    }

    private void initComponent() {

        buttonEmail = findViewById(R.id.imgbtn_email);
        buttonEmail.setOnClickListener(new ImageButtonEmailListener());
        buttonFacebook = findViewById(R.id.imgbtn_facebook);
        buttonFacebook.setOnClickListener(new ImageButtonFacebookListener());
        buttonWeb = findViewById(R.id.imgbtn_web);
        buttonWeb.setOnClickListener(new ImageButtonWebListener());
        buttonLokasi = findViewById(R.id.btnLokasi);
        buttonLokasi.setOnClickListener(new ButtonLokasiListener());
        buttonTwitter = findViewById(R.id.imgbtn_twitter);
        buttonTwitter.setOnClickListener(new ImageButtonTwitterListener());

    }


    private class ImageButtonEmailListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "contact@pdamtkr.co.id", null));
            bundleAnimation = ActivityOptions.makeCustomAnimation(LokasiKantorActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(intentEmail, bundleAnimation);
        }
    }

    private class ImageButtonFacebookListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent browserFacebook = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tirta.kertaraharja"));
            bundleAnimation = ActivityOptions.makeCustomAnimation(LokasiKantorActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(browserFacebook, bundleAnimation);
        }
    }

    private class ImageButtonWebListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent browserWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pdamtkr.co.id/"));
            bundleAnimation = ActivityOptions.makeCustomAnimation(LokasiKantorActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(browserWeb, bundleAnimation);
        }
    }

    private class ImageButtonTwitterListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent browserTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/contact_pdamtkr"));
            bundleAnimation = ActivityOptions.makeCustomAnimation(LokasiKantorActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(browserTwitter, bundleAnimation);
        }
    }
    private class ButtonLokasiListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intentLokasi = new Intent(LokasiKantorActivity.this, LokasiKantorHasilActivity.class);
            bundleAnimation = ActivityOptions.makeCustomAnimation(LokasiKantorActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
            startActivity(intentLokasi, bundleAnimation);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
    }


    /* Menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuback, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                overridePendingTransition(R.anim.page_slide_left, R.anim.page_slide_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
