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

import com.miamor.Obj.CampaignPictures;
import com.miamor.Obj.Campaigns;
import com.miamor.Obj.Picture;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class CouponListAdapter extends ArrayAdapter<Campaigns> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<Campaigns> mData;
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

    public CouponListAdapter(FragmentActivity activity, Context context, int resource, List<Campaigns> items) {
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
    public Campaigns getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_coupon, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageView);
        TextView txt=(TextView)vi.findViewById(R.id.ItemdescriptionCoupon);

        if(getCount()>0) {
            Campaigns camp = getItem(position);

            if(camp.getPictures()!=null && camp.getPictures().iterator().hasNext()){
                CampaignPictures pic=(CampaignPictures)((ArrayList)camp.getPictures()).get(0);
                Picasso.with(mContext).load(pic.getMediumBaseUrl()).into(img);
            }else{
                img.setImageBitmap(null);
            }

            txt.setText(camp.getShortDescription());
        }
        return vi;
    }
}
