package com.sdj_jewellers.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdj_jewellers.CartActivity;
import com.sdj_jewellers.R;
import com.sdj_jewellers.model.CartModel;
import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Utils;

import java.util.List;

/**
 * Created by Administrator on 6/20/2017.
 */

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder> {

    List<CartModel> mListData;
    Context ctx;
    Typeface roboto_regular;
    public CartListAdapter(Context ctx, List<CartModel> mListData) {
        this.mListData = mListData;
        this.ctx=ctx;
        roboto_regular =Utils.getCustomFont(ctx, FontType.ROBOTO_REGULAR);
    }

    @Override
    public CartListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_list_item,
                viewGroup, false);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Uri gmmIntentUri = Uri.parse("google.navigation:q="+mListData.get(i).getLatitude()+","+mListData.get(i).getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                ctx.startActivity(mapIntent);*/
            }
        });
        return new CartListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartListAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.title.setText(mListData.get(i).getCart_postName());
        myViewHolder.itemQuantity.setText(mListData.get(i).getCart_quantity());
        Glide.with(ctx)
                .load(mListData.get(i).getCart_imageUrl())
                .placeholder(R.drawable.place_holder)
                .into(myViewHolder.catImage);
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

        ImageView catImage;
        TextView title,weight,itemQuantity,edit,removeItem;

        public MyViewHolder(View itemView) {
            super(itemView);

            catImage = (ImageView) itemView.findViewById(R.id.catImageView);
            title= (TextView) itemView.findViewById(R.id.catTitle);
            weight= (TextView) itemView.findViewById(R.id.catWeightText);
            itemQuantity= (TextView) itemView.findViewById(R.id.itemQuantity_text);
            edit= (TextView) itemView.findViewById(R.id.itemEdit_text);
            removeItem= (TextView) itemView.findViewById(R.id.cartItemRemove_btn);

            title.setTypeface(roboto_regular);

            removeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAlertPrompt(ctx,"Alert","Are you sure , you want to remove "+mListData.get(getAdapterPosition()).getCart_postName()+" from cart ?",
                            getAdapterPosition(),mListData.get(getAdapterPosition()).getCartId());
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.showQuantityPrompt(ctx,mListData.get(getAdapterPosition()).getCart_postName(),getAdapterPosition(),"Please Enter quantity to order",1,
                            mListData.get(getAdapterPosition()).getCart_quantity(),"CartScreen");
                }
            });
        }
    }


    public static void showAlertPrompt(final Context context, String title, String message, final int position, final String cartId){

        LayoutInflater inflater=LayoutInflater.from(context);
        View prompt_view=inflater.inflate(R.layout.alert_prompt, null);
       final Dialog dialog11=new Dialog(context);
        dialog11.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog11.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog11.setContentView(prompt_view);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog11.getWindow().setLayout((6 * width)/9, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);

        final TextView title_txt= (TextView) prompt_view.findViewById(R.id.dialog_title_text);
        //     title_txt.setTypeface(roboto_ligh);
        title_txt.setText(title);
        TextView message_txt= (TextView) prompt_view.findViewById(R.id.messageText);
        message_txt.setText(message);

        TextView okButton= (TextView) prompt_view.findViewById(R.id.OK_btn);
        TextView cancelButton= (TextView) prompt_view.findViewById(R.id.cancel_btn);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CartActivity)context).deleteItem(position,cartId);
                dialog11.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog11.dismiss();
            }
        });

        dialog11.show();

    }

}
