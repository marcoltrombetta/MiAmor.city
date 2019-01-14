package com.miamor.Fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.miamor.ListAdapter.ProductListAdapter;
import com.miamor.Obj.Globals;
import com.miamor.Obj.bundleParams;
import com.miamor.R;
import com.miamor.Obj.Product;
import com.miamor.Runnables.ProductRunnable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class PlaceholderProductFragment extends ListFragment implements OnScrollListener{
	private List<Product> listData=new ArrayList<Product>();
	ListView lv;
	private int currentPage=1;
	private GridView gridview ;
	bundleParams params;

	public PlaceholderProductFragment() {}
		
	 ProductRunnable.ProductInterface productInterface=new ProductRunnable.ProductInterface() {
			
			@Override
			public void Completed(final Collection<Product>  response) {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {	
						if(response!=null){
							fillListViewData(response);
						}
					  }
					});
			}
			
			@Override
			public void onStart() {
				getActivity().runOnUiThread(new Runnable() {
					public void run() {		
			          
					}
				});
			}
		};

	private void fillListViewData(Collection<Product> data){
		listData.addAll(data);
		
		 // Each row in the list stores country name, currency and flag
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
 
        for(int i=0;i<listData.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            
            hm.put("name", listData.get(i).getName());
            aList.add(hm); 
        }
 
        // Keys used in Hashmap
        String[] from = { "name" };
 
        // Ids of views in listview_layout
        int[] to = { R.id.ItemdescriptionProduct};
 
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
		int pos=lv.getLastVisiblePosition();
		ProductListAdapter adapter = new ProductListAdapter(getActivity(),getActivity().getBaseContext(),R.layout.list_item_product, listData);
		setListAdapter(adapter);
		lv.setSelection(pos);
        adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.startSync();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_product, container, false);
		 listData.clear();

		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	   @Override
	    public void onViewCreated(View view, Bundle savedInstanceState) {
		   Bundle args = getArguments();
		   params= (bundleParams)args.getSerializable("values");

	   	 // Getting listview from xml
	        lv = getListView();
	        
	        // Adding button to listview at footer
		   lv.setOnScrollListener(this);

		   // Adding button to listview at footer
		   lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> parent, View v,
									   int position, long id) {
				   Bundle b = new Bundle();
				   params.setProductId(listData.get(position).getId());
				   b.putSerializable("values", params);

				   PlaceholderProductDetailsFragment f2=new PlaceholderProductDetailsFragment();
				   f2.setArguments(b);

				   Globals.changePagerFragment(getActivity(), f2,getFragmentManager(),"vendorproductdetails");
			   }
		   });

	        super.onViewCreated(view, savedInstanceState);
	   }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id==android.R.id.home){

		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE) {
			if (lv.getLastVisiblePosition() >= lv.getCount() - 1 ) {
				currentPage++;
				this.startSync();
			}
		}
	}

	private void startSync(){
		new Thread(new ProductRunnable(params.getVendorId(),params.getCategoryId(), currentPage, productInterface)).start();
	}
}
