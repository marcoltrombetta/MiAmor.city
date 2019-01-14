package com.miamor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.ArrayList;

import com.miamor.Fragments.*;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.viewpagerindicator.TabPageIndicator;

import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
	private CustomPagerAdapter customPagerAdapter;
	private ViewPager pager;
	private TabPageIndicator indicator;
	private ArrayList<Integer> listIcon;
	private ArrayList<Fragment> fragmentContents;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getTabsIcon();
		customPagerAdapter = new CustomPagerAdapter(
				getSupportFragmentManager(), listIcon, fragmentContents);

		pager = (ViewPager) findViewById(R.id.pager);

		pager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				return true;
			}
		});

		pager.setAdapter(customPagerAdapter);

		indicator = (TabPageIndicator) findViewById(R.id.tabs);
		//indicator.setTabIconLocation(TabPageIndicator.LOCATION_UP);
		indicator.setViewPager(pager);

		ViewPager pager = (ViewPager) this.findViewById(R.id.pager);
		CustomPagerAdapter customPagerAdapter =(CustomPagerAdapter)pager.getAdapter();
		int pos=customPagerAdapter.addItem(PlaceholderMainFrameFragment.newInstance());
		pager.setAdapter(customPagerAdapter);

		pager.setAnimationCacheEnabled(false);
		indicator.setAnimationCacheEnabled(false);

		indicator.setAnimation(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 	{
		menu.clear();

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){
			Globals.homeButtonHandler(this);
		}

		return super.onOptionsItemSelected(item);
	}

	private void getTabsIcon() {
		listIcon = new ArrayList<Integer>();

		listIcon.add(android.R.drawable.ic_menu_preferences);
		listIcon.add(android.R.drawable.ic_menu_search);
		listIcon.add(android.R.drawable.ic_menu_preferences);
		listIcon.add(android.R.drawable.ic_menu_preferences);
		
		fragmentContents = new ArrayList<Fragment>();
		fragmentContents.add(PlaceholderCategoryVendorFragment.newInstance());
		fragmentContents.add(PlaceholderSearchFragment.newInstance());
		fragmentContents.add(PlaceholderBlogFragment.newInstance());
		fragmentContents.add(PlaceholderProfileFragment.newInstance());

	}

}
