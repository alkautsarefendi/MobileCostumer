package com.fortuna.android.mobilecustomer.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import com.fortuna.android.mobilecustomer.R;
import com.fortuna.android.mobilecustomer.lokasi.LokasiKantorHasilActivity;
import com.fortuna.android.mobilecustomer.util.Util;
import com.fortuna.android.mobilecustomer.view.AndroidDataAdapter;
import com.fortuna.android.mobilecustomer.view.AndroidVersion;
import com.fortuna.android.mobilecustomer.view.CustomTypefaceSpan;
import com.fortuna.android.mobilecustomer.view.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by Stuarez on 11/30/2016.
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    DrawerLayout drawer;
    private CoordinatorLayout coordinator_lay;
    private NavigationView navigationView;
    private float lastTranslate = 0.0f;

    public AlertDialog myDialog;
    public static final int MULTIPLE_PERMISSIONS = 100;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private final String recyclerViewTitleText[] = {"Informasi Tagihan", "Status Pendaftaran", "Berita PDAM DEMO", "Pengaduan Pelanggan", "Lokasi Pembayaran"};
    private final String recyclerViewSubTitleText[] = {"Informasi Tagihan Pelanggan", "Informasi Status Pendaftaran", "Menampilkan Berita PDAM DEMO",
            "Pengaduan Pelanggan PDAM DEMO", "Lokasi Pembayaran Tagihan"};

    private final int recyclerViewImages[] = {
            R.mipmap.info_tagihan_new1, R.mipmap.status_pendaftaran_new, R.mipmap.berita_new,
            R.mipmap.pengaduan_new, R.mipmap.lokasi_bayar_new1
    };

    private Bundle bundleAnimation;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home);

        setupToolbar();
        setupCollapsingToolbar();
        setupDrawer();
        setupNavigationView();
        setupFloatingButton();

        initData();
        checkIzin();

    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinator_lay = findViewById(R.id.coordinator_lay);

    }

    private void setupCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapse_toolbar);

        if (collapsingToolbar != null) {
            collapsingToolbar.setTitleEnabled(false);
        }
    }

    private void setupDrawer() {
        drawer = findViewById(R.id.drawer_layout);
        drawer.setScrimColor(getResources().getColor(android.R.color.transparent));
        drawer.getStatusBarBackgroundDrawable();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (navigationView.getWidth() * slideOffset);

                coordinator_lay.setTranslationX(moveFactor);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        if (navigationView != null) {
            Menu m = navigationView.getMenu();
            for (int i = 0; i < m.size(); i++) {
                MenuItem mi = m.getItem(i);

                //for aapplying a font to subMenu ...
                SubMenu subMenu = mi.getSubMenu();
                if (subMenu != null && subMenu.size() > 0) {
                    for (int j = 0; j < subMenu.size(); j++) {
                        MenuItem subMenuItem = subMenu.getItem(j);
                        applyFontToMenuItem(subMenuItem);
                    }
                }

                //the method we have create in activity
                applyFontToMenuItem(mi);
            }
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    private void setupFloatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentEmail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "contact@pdamtkr.co.id", null));
                    bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                    startActivity(intentEmail, bundleAnimation);
                }
            });
        }
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Arial.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    private void initData() {

        mRecyclerView = findViewById(R.id.list_rv);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
        }
        mLayoutManager = new GridLayoutManager(HomeActivity.this, 1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<AndroidVersion> av = prepareData();
        AndroidDataAdapter mAdapter = new AndroidDataAdapter(HomeActivity.this, av);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onItemClick(View view, int i) {

                        switch (i) {
                            case 0:
                                Intent infoTagihan = new Intent(HomeActivity.this, InformasiTagihanPelangganActivity.class);
                                String strInfoTagihan = "Info Tagihan";
                                infoTagihan.putExtra("InfoTagihan", strInfoTagihan);
                                bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                                startActivity(infoTagihan, bundleAnimation);
                                break;
                            case 1:
                                Intent statusDaftar = new Intent(HomeActivity.this, StatusPendaftaranPelangganActivity.class);
                                String strStatusDaftar = "Status Daftar";
                                statusDaftar.putExtra("StatusDaftar", strStatusDaftar);
                                bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                                startActivity(statusDaftar, bundleAnimation);
                                break;
                            case 2:
                                Intent berita = new Intent(HomeActivity.this, BeritaPDAMActivity.class);
                                String strBerita = "Berita";
                                berita.putExtra("berita", strBerita);
                                bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                                startActivity(berita, bundleAnimation);
                                break;
                            case 3:
                                Util.showmsg(HomeActivity.this, null, "Anda tidak di izinkan mengakses menu ini");
                                /*Intent pengaduan = new Intent(HomeActivity.this, PengaduanPelangganActivity.class);
                                String strPengaduan = "Pengaduan";
                                pengaduan.putExtra("pengaduan", strPengaduan);
                                bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                                startActivity(pengaduan, bundleAnimation);*/
                                break;
                            case 4:
                                Intent lokasi = new Intent(HomeActivity.this, LokasiKantorActivity.class);
                                String strLokasi = "Lokasi";
                                lokasi.putExtra("lokasi", strLokasi);
                                bundleAnimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.page_animation, R.anim.page_animation1).toBundle();
                                startActivity(lokasi, bundleAnimation);
                                break;
                        }

                    }
                })
        );

    }

    private ArrayList<AndroidVersion> prepareData() {

        ArrayList<AndroidVersion> av = new ArrayList<>();
        for (int i = 0; i < recyclerViewTitleText.length; i++) {
            AndroidVersion mAndroidVersion = new AndroidVersion();
            mAndroidVersion.setrecyclerViewImage(recyclerViewImages[i]);
            mAndroidVersion.setrecyclerViewTitleText(recyclerViewTitleText[i]);
            mAndroidVersion.setRecyclerViewSubTitleText(recyclerViewSubTitleText[i]);
            av.add(mAndroidVersion);
        }
        return av;
    }


    private void checkIzin() {

        String[] Permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        if (!hasPermissions(this, Permissions)) {

            ActivityCompat.requestPermissions(this, Permissions, MULTIPLE_PERMISSIONS);

        } else {

            Log.v("Loc Permission", "Permission is Allow");

        }

    }

    public boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {

            case MULTIPLE_PERMISSIONS:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                    Log.v("Loc Permission", "Permission is granted");

                } else {

                    //You did not accept the request can not use the functionality.
                    Log.v("Loc Permission", "Permission is revoked");
                    showDialogNotGrant(HomeActivity.this, getString(R.string.msg_izin));

                }

                if (grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                    Log.v("Loc Coarse Permission", "Permission is granted");

                } else {

                    //You did not accept the request can not use the functionality.
                    Log.v("Loc Coarse Permission", "Permission is revoked");
                    showDialogNotGrant(HomeActivity.this, getString(R.string.msg_izin));

                }

                break;

        }

    }

    public void showDialogNotGrant(Context context, String message) {

        if (myDialog != null && myDialog.isShowing()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Tutup Aplikasi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setCancelable(false);
        myDialog = builder.create();
        myDialog.show();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Double Click to Close Application", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            this.finish();

        } else if (id == R.id.nav_cek_tagihan) {

            Intent cekTagihan = new Intent(HomeActivity.this, InformasiTagihanPelangganActivity.class);
            startActivity(cekTagihan);

        } else if (id == R.id.nav_status_pendaftaran) {

            Intent statusDaftar = new Intent(HomeActivity.this, StatusPendaftaranPelangganActivity.class);
            startActivity(statusDaftar);

        } else if (id == R.id.nav_berita) {

            Intent berita = new Intent(HomeActivity.this, BeritaPDAMActivity.class);
            startActivity(berita);

        } else if (id == R.id.nav_pengaduan) {

            Util.showmsg(HomeActivity.this, null, "Anda tidak di izinkan mengakses menu ini");

            /*Intent pengaduan = new Intent(HomeActivity.this, PengaduanPelangganActivity.class);
            startActivity(pengaduan);*/

        } else if (id == R.id.nav_lokasi) {

            Intent lokasi = new Intent(HomeActivity.this, LokasiKantorActivity.class);
            startActivity(lokasi);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}
