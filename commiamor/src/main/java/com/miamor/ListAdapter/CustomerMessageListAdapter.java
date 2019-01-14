package com.miamor.ListAdapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.miamor.Obj.CustomerMessage;
import com.miamor.R;

import java.util.List;

/**
 * Created by Marcot on 8/10/2015.
 */

public class CustomerMessageListAdapter extends ArrayAdapter<CustomerMessage> {
    public LayoutInflater inflater = null;
    private Context mContext;
    List<CustomerMessage> mData;
    FragmentActivity activity;

    public CustomerMessageListAdapter(FragmentActivity activity, Context context, int resource, List<CustomerMessage> items) {
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
    public CustomerMessage getItem(int position) {
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
            vi = inflater.inflate(R.layout.list_item_message, null);

        TextView txtdesc=(TextView)vi.findViewById(R.id.Itemdescriptionmes);
        TextView txttitle=(TextView)vi.findViewById(R.id.itemtitle);
        TextView txtdate=(TextView)vi.findViewById(R.id.itemdatesend);

        if(getCount()>0) {
            CustomerMessage msg = getItem(position);

            txttitle.setText(msg.getTitle());
            txtdesc.setText(msg.getMsgTxt());

        }
        return vi;
    }
}
