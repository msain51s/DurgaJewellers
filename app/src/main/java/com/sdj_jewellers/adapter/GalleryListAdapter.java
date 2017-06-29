package com.sdj_jewellers.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdj_jewellers.CategoryInfoActivity;
import com.sdj_jewellers.GalleryActivity;
import com.sdj_jewellers.R;
import com.sdj_jewellers.model.GalleryModel;
import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Utils;

import java.util.List;

/**
 * Created by bhanwar on 13/06/2017.
 */

public class GalleryListAdapter extends RecyclerView.Adapter<GalleryListAdapter.MyViewHolder> {

    List<GalleryModel> mListData;
    GalleryActivity ctx;
    Typeface montserrat_regular;
    public GalleryListAdapter(GalleryActivity ctx, List<GalleryModel> mListData) {
        this.mListData = mListData;
        this.ctx=ctx;
        montserrat_regular= Utils.getCustomFont(ctx, FontType.MONESTER_RAT_REGULAR);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup,final int i) {
        View view=null;
        if(i%2==0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_list_item,
                    viewGroup, false);
        }else{
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_list_item1,
                    viewGroup, false);
        }

        Log.d("position",""+i);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, CategoryInfoActivity.class);
                intent.putExtra("catId",mListData.get(i).getTermId());
                intent.putExtra("title",mListData.get(i).getName());
                ctx.startActivity(intent);
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mListData.get(i).getName());
        myViewHolder.itemCount.setText(mListData.get(i).getProductCount()+" Items");
        if(i==0) {
            if(mListData.get(i).getImageUrl()==null)
            Glide.with(ctx)
                    .load(R.drawable.category_banner_01)
                    .placeholder(R.drawable.place_holder)
                    .into(myViewHolder.imageView);
            else
                Glide.with(ctx)
                        .load(mListData.get(i).getImageUrl())
                        .placeholder(R.drawable.place_holder)
                        .into(myViewHolder.imageView);
        }
        else if(i==1) {
            if(mListData.get(i).getImageUrl()==null)
            Glide.with(ctx)
                    .load(R.drawable.category_banner_02)
                    .placeholder(R.drawable.place_holder)
                    .into(myViewHolder.imageView);
            else
                Glide.with(ctx)
                        .load(mListData.get(i).getImageUrl())
                        .placeholder(R.drawable.place_holder)
                        .into(myViewHolder.imageView);
        }
        else if(i==2) {
            if(mListData.get(i).getImageUrl()==null)
            Glide.with(ctx)
                    .load(R.drawable.category_banner_03)
                    .placeholder(R.drawable.place_holder)
                    .into(myViewHolder.imageView);
            else
                Glide.with(ctx)
                        .load(mListData.get(i).getImageUrl())
                        .placeholder(R.drawable.place_holder)
                        .into(myViewHolder.imageView);
        }
        else{
            if(mListData.get(i).getImageUrl()!=null)
                Glide.with(ctx)
                        .load(mListData.get(i).getImageUrl())
                        .placeholder(R.drawable.place_holder)
                        .into(myViewHolder.imageView);
        }
    }

    public void removeItem(int position) {
        mListData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mListData.size());
    }
    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,itemCount;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.imageNameText);
            imageView= (ImageView) itemView.findViewById(R.id.image_view);
            itemCount= (TextView) itemView.findViewById(R.id.noOfItemText);

            title.setTypeface(montserrat_regular);
            itemCount.setTypeface(montserrat_regular);

            DisplayMetrics metrics = ctx.getResources().getDisplayMetrics();
         //   int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            imageView.getLayoutParams().height=height/3;
        }
    }

}


