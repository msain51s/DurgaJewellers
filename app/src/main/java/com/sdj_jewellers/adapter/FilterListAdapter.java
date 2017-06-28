package com.sdj_jewellers.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sdj_jewellers.R;

import java.util.List;

/**
 * Created by Administrator on 6/19/2017.
 */

public class FilterListAdapter extends ArrayAdapter {

    private List<String> filterList;
    Typeface roboto_light=null;
    RadioButton selected=null;
    Context context;

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final MyViewHolder holder;

        if (view == null){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dialog_list_item, parent, false);
            holder=new MyViewHolder();
            holder.radioButton= (RadioButton) view.findViewById(R.id.dialog_radio_btn);

            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(selected != null)
                    {
                        selected.setChecked(false);
                    }

                    holder.radioButton.setChecked(true);
                    selected = holder.radioButton;
                }
            });

            view.setTag(holder);
        }
        else
            holder = (MyViewHolder) view.getTag();

        holder.radioButton.setText(filterList.get(position));


        return view;
    }

    public class MyViewHolder  {
        public TextView user_type_name ;
        public RadioButton radioButton;

    }
    public FilterListAdapter(Context context,List<String> list) {
        super(context,R.layout.dialog_list_item,list);
        this.filterList = list;
//        roboto_light= Utils.getCustomFont(Application.mContext, FontType.ROBOTO_LIGHT);
    }
}


