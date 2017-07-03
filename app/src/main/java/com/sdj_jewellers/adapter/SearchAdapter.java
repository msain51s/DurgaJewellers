package com.sdj_jewellers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdj_jewellers.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 7/3/2017.
 */

public class SearchAdapter extends BaseAdapter /*implements Filterable */{

    private ArrayList<String> data;

    private String[] typeAheadData;

    LayoutInflater inflater;
    Context context;

    public SearchAdapter(Context context) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        data = new ArrayList<String>();
        typeAheadData = context.getResources().getStringArray(R.array.state_array_full);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        String currentListData = (String) getItem(position);

        mViewHolder.textView.setText(currentListData);

        return convertView;
    }


    private class MyViewHolder {
        TextView textView;

        public MyViewHolder(View convertView) {
            textView = (TextView) convertView.findViewById(android.R.id.text1);
        }
    }
}

