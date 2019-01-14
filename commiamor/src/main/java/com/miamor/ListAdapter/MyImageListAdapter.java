package com.miamor.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class MyImageListAdapter  extends ArrayAdapter<String> {
    public LayoutInflater inflater = null;
    private Context mContext;
    int count;
    List<String> mData;
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

    public MyImageListAdapter(FragmentActivity activity,Context context, int resource, List<String> items) {
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
    public String getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_image, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageView);

        if(getCount()>0) {
            String d = getItem(position);
            Picasso.with(mContext).load(d).into(img);
        }

        return vi;
    }
}
