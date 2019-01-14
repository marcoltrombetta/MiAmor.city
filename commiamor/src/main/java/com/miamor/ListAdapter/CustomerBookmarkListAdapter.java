package com.miamor.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miamor.Obj.Addresses;
import com.miamor.Obj.CustomerBookmark;
import com.miamor.Obj.Picture;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class CustomerBookmarkListAdapter extends ArrayAdapter<CustomerBookmark> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<CustomerBookmark> mData;
    FragmentActivity activity;

    LoadImageRunnable.ImageInteface imageInterface=new LoadImageRunnable.ImageInteface(){
        @Override
        public void loadComplete(final Bitmap bmp, final ImageView v) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    v.setImageBitmap(bmp);
                }
            });
        }
    };

    private void startSync(String url, ImageView img){
        new Thread(new LoadImageRunnable(imageInterface,url,img)).start();
    }

    public CustomerBookmarkListAdapter(FragmentActivity activity, Context context, int resource, List<CustomerBookmark> items) {
        super(context, resource, items);
        mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(items!=null) {
            mData = items;
        }

        this.activity=activity;
    }

    @Override
    public CustomerBookmark getItem(int position) {
            return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return  mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_customerbookmark, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageView);
        TextView name=(TextView)vi.findViewById(R.id.customerbookmarkvendorName);
        TextView phone=(TextView)vi.findViewById(R.id.customerbookmarkvendorPhone);
        TextView address=(TextView)vi.findViewById(R.id.customerbookmarkvendorAddress);

        if(getCount()>0) {
            CustomerBookmark customerBookmark = getItem(position);

            name.setText(customerBookmark.getVendor().getName());

            if(customerBookmark.getVendor()!=null && customerBookmark.getVendor().getAddresses().iterator().hasNext()) {
                Addresses addr = customerBookmark.getVendor().getAddresses().iterator().next();
                address.setText(addr.getAddress1());
                phone.setText(addr.getPhoneNumber());
            }

            if(customerBookmark.getVendor()!=null && customerBookmark.getVendor().getVendorPictures().iterator().hasNext()){
                Picture pic=customerBookmark.getVendor().getVendorPictures().iterator().next().getPicture();
                Picasso.with(mContext).load(pic.getBaseUrl()).into(img);
            }else{
                img.setImageBitmap(null);
            }

        }
        return vi;
    }
}
