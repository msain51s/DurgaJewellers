package com.sdj_jewellers;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public FrameLayout frameLayout;
Preference preference;
    Toolbar toolbar;
    ImageView toolbarCart;
    TextView toolbarTitle,cart_countText;
    View cartIconView;
    Typeface montesarrat_bold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
       /* DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_layout, null);
        frameLayout= (FrameLayout) fullView.findViewById(R.id.content_frame);
        getLayoutInflater().inflate(R.layout.activity_base, frameLayout, true);
        super.setContentView(fullView);*/

        preference=new Preference(this);
        montesarrat_bold= Utils.getCustomFont(this, FontType.MONESTER_RAT_BOLD);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarCart = (ImageView) findViewById(R.id.toolbar_cart_icon);
        toolbarTitle= (TextView) findViewById(R.id.toolbar_title_text);
        toolbarTitle.setTypeface(montesarrat_bold);


        cart_countText= (TextView) findViewById(R.id.cart_countText);
        cartIconView=findViewById(R.id.cartLayout);
        frameLayout= (FrameLayout) findViewById(R.id.content_frame);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(ColorStateList.valueOf(Color.WHITE));


        cartIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BaseActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(BaseActivity.this,HomeActivity.class));
        } else if (id == R.id.nav_gallery) {
            Intent intent=new Intent(BaseActivity.this,GalleryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_education) {
            startActivity(new Intent(BaseActivity.this,EventActivity.class));
        } else if (id == R.id.nav_basket) {
            Intent intent=new Intent(BaseActivity.this,CartActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {
            preference.clearAllPrefereces();
            Intent intent=new Intent(BaseActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }  else if (id == R.id.nav_about_us) {
            startActivity(new Intent(BaseActivity.this,AboutUsActivity.class));
        }else if (id == R.id.nav_reach_us) {
            startActivity(new Intent(BaseActivity.this,ReachUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
