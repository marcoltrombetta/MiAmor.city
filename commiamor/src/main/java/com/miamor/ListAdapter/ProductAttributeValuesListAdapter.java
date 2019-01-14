package com.miamor.ListAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.miamor.Obj.Product;
import com.miamor.Obj.ProductAttributeValues;
import com.miamor.R;
import com.miamor.Runnables.LoadImageRunnable;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class ProductAttributeValuesListAdapter extends ArrayAdapter<ProductAttributeValues> {
    public LayoutInflater inflater = null;
    private Context mContext;
    int count;
    List<ProductAttributeValues> mData;
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

    public ProductAttributeValuesListAdapter(FragmentActivity activity, Context context, int resource, List<ProductAttributeValues> items) {
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
    public ProductAttributeValues getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_productattributevalue, null);

        TextView title=(TextView)vi.findViewById(R.id.ItemdescriptionProduct);
        TextView name=(TextView)vi.findViewById(R.id.ItemNameProduct);

        if(getCount()>0) {
            ProductAttributeValues prod = getItem(position);

            title.setText(prod.getProductAttribute().get(0));

           for(int i=1;i<prod.getProductAttribute().size();i++) {
               if(i % 5 ==0){
                   title.setText(Html.fromHtml(title.getText() + "</br>"));
               }
                title.setText(title.getText() + ", " + prod.getProductAttribute().get(i));
            }

            name.setText(prod.getName());
        }

        return vi;
    }
}
