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

import com.miamor.Obj.Picture;
import com.miamor.Obj.Product;
import com.miamor.Obj.ProductPicture;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class ProductListAdapter extends ArrayAdapter<Product> {
    public LayoutInflater inflater = null;
    private Context mContext;
    int count;
    List<Product> mData;
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

    public ProductListAdapter(FragmentActivity activity, Context context, int resource, List<Product> items) {
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
    public Product getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_product, null);

        ImageView img=(ImageView)vi.findViewById(R.id.imageViewProduct);
        TextView title=(TextView)vi.findViewById(R.id.ItemdescriptionProduct);
        TextView price=(TextView)vi.findViewById(R.id.ItemPriceProduct);
        TextView name=(TextView)vi.findViewById(R.id.ItemNameProduct);

        if(getCount()>0) {
            Product prod = getItem(position);

            if(prod.getPicture()!=null) {
                startSync(prod.getPicture().getBaseUrl(), img);
            }else{
                img.setImageBitmap(null);
            }

            title.setText(prod.getShortDescription());
            price.setText("$"+Float.toString(prod.getPrice()));
            name.setText(prod.getName());
        }

        return vi;
    }
}
