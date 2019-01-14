package com.miamor.Fragments;

import java.util.ArrayList;

import com.viewpagerindicator.IconPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter implements
IconPagerAdapter {

	private ArrayList<Integer> listIcon;
	private ArrayList<Fragment> pageContents;
	
	public CustomPagerAdapter(FragmentManager fm, ArrayList<Integer> list,
			ArrayList<Fragment> content) {
			super(fm);
			this.listIcon = list;
			this.pageContents = content;
	}

	@Override
	public Fragment getItem(int position) {
		return pageContents.get(position);
	}
	
	public int addItem(Fragment fragment){
		pageContents.add(fragment);
		return pageContents.size();
	}

	public void removeItem(int i){
		pageContents.remove(i);
	}

	@Override
	public int getCount() {
		return pageContents.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0) {
			return "Category";
		} else if (position == 1) {
			return "Search";
		} else if (position == 2) {
			return "Blog";
		} else if (position == 3){
			return "Profile";
		}else {
			return "";
			}
	}

	@Override
	public int getIconResId(int index) {
		return listIcon.get(index);
	}

}
