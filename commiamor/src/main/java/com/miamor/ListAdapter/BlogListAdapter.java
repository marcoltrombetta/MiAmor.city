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

import com.miamor.Obj.Blog;
import com.miamor.Obj.BlogPicture;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class BlogListAdapter extends ArrayAdapter<Blog> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<Blog> mData;
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

    public BlogListAdapter(FragmentActivity activity, Context context, int resource, List<Blog> items) {
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
    public Blog getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_blog, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageView);
        TextView txt=(TextView)vi.findViewById(R.id.ItemdescriptionCoupon);
        TextView likes=(TextView)vi.findViewById(R.id.TotalLikesCoupon);
        TextView shortdesc=(TextView)vi.findViewById(R.id.shortdescCoupon);

        if(getCount()>0) {
            Blog blog = getItem(position);

            BlogPicture pic=blog.getDefaultPic();
            if(pic!=null){
                Picasso.with(mContext).load(pic.getMediumBaseUrl()).into(img);
            }
            likes.setText(blog.getTotalLikes()+" likes");
            shortdesc.setText(blog.getShortDescription());
            txt.setText(blog.getTitle());
        }
        return vi;
    }
}
