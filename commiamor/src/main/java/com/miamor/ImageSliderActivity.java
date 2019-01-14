package com.miamor;

/**
 * Created by marco on 10/12/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ImageSliderActivity extends Activity {
    private List<String> images=new ArrayList<String>();
    public ImageSliderActivity(){}

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.getActionBar().setHomeButtonEnabled(true);

       Bundle extras = getIntent().getExtras();
       images=(ArrayList<String>)extras.getSerializable("photos");

       // Set portrait orientation
       setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);

       setContentView(R.layout.imageslider);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private class ImageAdapter extends PagerAdapter {
        Context context;

        ImageAdapter(Context context){
            this.context=context;
        }
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            int padding = 10;
            imageView.setPadding(padding, padding, padding, padding);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Picasso.with(context).load(images.get(position)).centerCrop().resize(1024, 768).into(imageView);

            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

    }
}
