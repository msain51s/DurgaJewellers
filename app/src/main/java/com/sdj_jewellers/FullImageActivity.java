package com.sdj_jewellers;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sdj_jewellers.adapter.FullImageViewPagerAdapter;
import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Utils;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class FullImageActivity extends AppCompatActivity implements View.OnTouchListener {

    Toolbar toolbar;
    TextView toolbarTitle;
    ViewPager viewPager;
    PagerAdapter adapter;
    CirclePageIndicator mIndicator;
    ArrayList<String> imageUrlList;
    int position;


    public final static String TAG="FullImageActivity";
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // We can be in one of these 3 states
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // Remember some things for zooming
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    String savedItemClicked;

    Typeface montesarrat_bold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        montesarrat_bold= Utils.getCustomFont(this, FontType.MONESTER_RAT_BOLD);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle= (TextView) findViewById(R.id.toolbar_title_text);
        toolbarTitle.setTypeface(montesarrat_bold);
        toolbarTitle.setText("Full Image");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            position=bundle.getInt("position");
            imageUrlList=bundle.getStringArrayList("list");
        }

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.full_image_viewPager);
        // Pass results to ViewPagerAdapter Class
        adapter = new FullImageViewPagerAdapter(this, imageUrlList);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        // ViewPager Indicator
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        mIndicator.setViewPager(viewPager);
        viewPager.setCurrentItem(position);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
