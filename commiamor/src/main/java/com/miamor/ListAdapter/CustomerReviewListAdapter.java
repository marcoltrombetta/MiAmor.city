package com.miamor.ListAdapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.miamor.Obj.CustomerReview;
import com.miamor.R;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class CustomerReviewListAdapter extends ArrayAdapter<CustomerReview> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<CustomerReview> mData;
    FragmentActivity activity;

    public CustomerReviewListAdapter(FragmentActivity activity, Context context, int resource, List<CustomerReview> items) {
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
    public CustomerReview getItem(int position) {
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
        TextView date=(TextView)vi.findViewById(R.id.CustomerDateReview);
        RatingBar rating=(RatingBar)vi.findViewById(R.id.ratingBarReview);

        if(getCount()>0) {
            CustomerReview venReview = getItem(position);

            date.setText(venReview.getCreatedOnUtc().toString());
            name.setText(venReview.getVendor().getName());
            desc.setText(venReview.getReviewText());
            rating.setRating(venReview.getRating());
        }
        return vi;
    }
}
