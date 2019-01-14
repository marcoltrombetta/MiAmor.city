package com.miamor.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miamor.Obj.Addresses;
import com.miamor.Obj.Globals;
import com.miamor.Obj.Vendor;
import com.miamor.Obj.VendorPictures;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class VendorListAdapter extends ArrayAdapter<Vendor> {
    public LayoutInflater inflater = null;
    private Context mContext;
    int count;
    List<Vendor> mData;
    FragmentActivity activity;

    LoadImageRunnable.ImageInteface imageInterface=new LoadImageRunnable.ImageInteface(){
        @Override
        public void loadComplete(final Bitmap bmp, final ImageView v) {
            if(activity!=null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        v.setImageBitmap(bmp);
                    }
                });
            }
        }
    };

    private void startSync(String url, ImageView img){
        new Thread(new LoadImageRunnable(imageInterface,url,img)).start();
    }

    public VendorListAdapter(FragmentActivity activity, Context context, int resource, List<Vendor> items) {
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
    public Vendor getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.list_item_vendor, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageViewVendor);
        TextView title=(TextView)vi.findViewById(R.id.itemdescriptionVendor);
        TextView address=(TextView)vi.findViewById(R.id.addressVendor);
        TextView coupons=(TextView)vi.findViewById(R.id.couponsVendor);
        TextView distance=(TextView)vi.findViewById(R.id.distanceVendor);
        TextView reviews=(TextView)vi.findViewById(R.id.reviewsVendor);
        RatingBar rating=(RatingBar)vi.findViewById(R.id.ratingBarVendor);

        if(getCount()>0) {
            Vendor ven = getItem(position);

            Addresses addr=null;
            Location loc=Globals.getLocation(activity);

            if(ven.getVendorPictures()!=null && ven.getVendorPictures().iterator().hasNext()){

                VendorPictures pic=(VendorPictures)((ArrayList)ven.getVendorPictures()).get(0);

                try{
                    Picasso.with(mContext).load(pic.getPicture().getBaseUrl()).into(img);
                }catch (Exception ex){
                    Picasso.with(mContext).load(pic.getBaseUrl()).into(img);
                }

            }else{
                img.setImageBitmap(null);
            }

            title.setText(ven.getName());

            if(ven.getAddresses()!=null && ven.getAddresses().iterator().hasNext()){
                addr=ven.getAddresses().iterator().next();
                address.setText(addr.getAddress1());
            }

            if(loc!=null) {
                distance.setText(Double.toString(Globals.getDistance(addr.getLatitude(), addr.getLongitude(), loc.getLatitude(), loc.getLongitude())) + " m");
            }

            coupons.setText(Integer.toString(ven.getCouponNum())+" coupons");
            reviews.setText(Integer.toString(ven.getApprovedTotalReviews())+" reviews");
            rating.setRating(ven.getApprovedRatingSum());
        }

        return vi;
    }
}
