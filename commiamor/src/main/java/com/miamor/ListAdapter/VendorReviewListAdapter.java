package com.miamor.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.miamor.Obj.VendorReview;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class VendorReviewListAdapter extends ArrayAdapter<VendorReview> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<VendorReview> mData;
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

    public VendorReviewListAdapter(FragmentActivity activity, Context context, int resource, List<VendorReview> items) {
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
    public VendorReview getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_review, null);

        TextView desc=(TextView)vi.findViewById(R.id.ItemdescriptionReview);
        TextView name=(TextView)vi.findViewById(R.id.CustomerNameReview);
        RatingBar rating=(RatingBar)vi.findViewById(R.id.ratingBarReview);
        ImageView img=(ImageView)vi.findViewById(R.id.imageViewReview);
        TextView date=(TextView)vi.findViewById(R.id.CustomerDateReview);

        if(getCount()>0) {
            VendorReview venReview = getItem(position);

            desc.setText(venReview.getReviewText());
            name.setText(venReview.getCustomer().getFirstName());
            rating.setRating(venReview.getRating());
            date.setText(venReview.getCreatedOnUtc().toString());

            if(venReview.getCustomer().getPicture()!=null){
                try{
                    if(venReview.getCustomer().getPicture().getPicture()!=null) {
                        Picasso.with(mContext).load(venReview.getCustomer().getPicture().getPicture().getBaseUrl()).into(img);
                    }else{
                        Picasso.with(mContext).load(venReview.getCustomer().getPicture().getBaseUrl()).into(img);
                    }
                }catch (Exception ex){
                    if(venReview.getCustomer().getPicture()!=null) {
                        Picasso.with(mContext).load(venReview.getCustomer().getPicture().getBaseUrl()).into(img);
                    }
                }
            }
        }
        return vi;
    }
}
