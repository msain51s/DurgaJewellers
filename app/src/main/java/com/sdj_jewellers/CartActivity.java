package com.sdj_jewellers;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sdj_jewellers.adapter.CartListAdapter;
import com.sdj_jewellers.db.DB_Handler;
import com.sdj_jewellers.model.CartModel;
import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity implements ResponseListener {
    RecyclerView recyclerView;
    TextView itemCountText;
    List<CartModel> list;
    CartListAdapter mAdapter;
    int selectedItemPosition=-1;
    Handler h;
    DB_Handler db_handler;
    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_cart);
        getLayoutInflater().inflate(R.layout.activity_cart,frameLayout);
        h = new Handler();
        db_handler=new DB_Handler(this);
        preference=new Preference(this);

        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText("Cart");
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Basket");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        itemCountText= (TextView) findViewById(R.id.itemCountTextView);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<CartModel>();

        mAdapter = new CartListAdapter(this, list);
        recyclerView.setAdapter(mAdapter);

        getCartListAndRefresh();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getCartListAndRefresh();
    }

    public void placeOrderClick(View view){
        if(list.size()>0)
            hitPlaceOrder();
        else
            Utils.showCommonInfoPrompt(this,"Alert","No item available in the cart to place order");
    }

 /*HIT FOR DELETING PRODUCT FROM LIST*/
  public void deleteItem(int position,String cartId){
    selectedItemPosition=position;
    deleteItemFromCart(cartId);
   }

    public void editItem(int position,String quantity){
            editCartProduct(list.get(position),quantity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                // Toast.makeText(this, "home pressed", Toast.LENGTH_LONG).show();
                break;

        }

        return true;
    }

    public void getCartListAndRefresh(){
       /* h.post(new Runnable() {
            @Override
            public void run() {
                list.clear();
                list.addAll(db_handler.getCartData());
                mAdapter.notifyDataSetChanged();
                itemCountText.setText(list.size()+" item in your bag");
            }
        });*/


     if (Utils.ChechInternetAvalebleOrNot(CartActivity.this)) {

        Utils.showLoader(CartActivity.this);
        ServerRequest
                .postRequest(
                        Connection.BASE_URL + "get_cartProducts",
                        getCartProductsData(preference.getUSER_ID()),
                        CartActivity.this,
                        ResponseListener.REQUEST_GET_CART_PRODUCTS_LIST);

    } else {
        //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
        Toast.makeText(CartActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
        return;
    }
}

    public JSONObject getCartProductsData(String userId) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

  /*DELETE CART ITEM*/
  public void deleteItemFromCart(String cartId){
      if (Utils.ChechInternetAvalebleOrNot(CartActivity.this)) {

          Utils.showLoader(CartActivity.this);
          ServerRequest
                  .postRequest(
                          Connection.BASE_URL + "delete_cartProducts",
                          getdeleteItemFromCartData(preference.getUSER_ID(),cartId),
                          CartActivity.this,
                          ResponseListener.REQUEST_REMOVE_ITEM_FROM_CART);

      } else {
          //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
          Toast.makeText(CartActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
          return;
      }
  }

    public JSONObject getdeleteItemFromCartData(String userId,String cartId) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("cartId", cartId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    /*UPDATE PRODUCTS QUANTITY*/
    public void editCartProduct(CartModel model,String quantity){
        if (Utils.ChechInternetAvalebleOrNot(CartActivity.this)) {

            Utils.showLoader(CartActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "update_cartProducts",
                            getEditCartProductData(preference.getUSER_ID(),model.getCartId(),quantity),
                            CartActivity.this,
                            ResponseListener.REQUEST_UPDATE_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CartActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getEditCartProductData(String userId,String cartId,String qty) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("cartId", cartId);
            json.put("qty", qty);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

/*PLACE ORDER*/
    public void hitPlaceOrder(){

        if (Utils.ChechInternetAvalebleOrNot(CartActivity.this)) {

            Utils.showLoader(CartActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "placeOrder",
                            getPlaceOrderData(preference.getUSER_ID(),preference.getEMAIL_ID()),
                            CartActivity.this,
                            ResponseListener.REQUEST_PLACE_ORDER);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CartActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public JSONObject getPlaceOrderData(String userId,String email) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("userEmail", email);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    @Override
    public void onResponse(final Response response, final int rid) {


        h.post(new Runnable() {

            @Override
            public void run() {
                Utils.dismissLoader();
                if (rid == ResponseListener.REQUEST_GET_CART_PRODUCTS_LIST) {

                    if (response.isError()) {
                        Toast.makeText(CartActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            list.clear();
                            CartModel model = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                jsonArray = jsonObject1.getJSONArray("record");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new CartModel();
                                    model.setCartId(jsonObject.getString("cart_id"));
                                    model.setCart_postId(jsonObject.getString("cart_postId"));
                                    model.setCart_postName(jsonObject.getString("cart_postName"));
                                    model.setCart_postExcerpt(jsonObject.getString("cart_postExcerpt"));
                                    model.setCart_imageUrl(jsonObject.getString("cart_imagePath"));
                                    model.setCart_categoryId(jsonObject.getString("cart_categoryId"));
                                    model.setCart_quantity(jsonObject.getString("cart_quantity"));

                                    list.add(model);
                                }

                                mAdapter.notifyDataSetChanged();
                                itemCountText.setText(list.size() + " item in your cart");
                                cart_countText.setVisibility(View.VISIBLE);
                                cart_countText.setText(""+list.size());
                                preference.setCART_COUNT(list.size());
                            }
                            } else {
                                cart_countText.setVisibility(View.GONE);
                                Utils.showCommonInfoPrompt(CartActivity.this,"Alert",jsonObject1.getString("msg"));
                //                Toast.makeText(CartActivity.this, jsonObject1.getString("msg"), Toast.LENGTH_LONG).show();
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_REMOVE_ITEM_FROM_CART) {

                    if (response.isError()) {
                        Toast.makeText(CartActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                Utils.showCommonInfoPrompt(CartActivity.this,"Success",jsonObject1.getString("msg"));
                                mAdapter.removeItem(selectedItemPosition);
                                itemCountText.setText(list.size()+" item in your cart");
                                preference.setCART_COUNT(list.size());
                                if(preference.getCART_COUNT()!=0) {
                                    cart_countText.setVisibility(View.VISIBLE);
                                    cart_countText.setText(""+preference.getCART_COUNT());
                                }else
                                    cart_countText.setVisibility(View.GONE);
                            } else{
                                Utils.showCommonInfoPrompt(CartActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_UPDATE_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(CartActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                Utils.showCommonInfoPrompt(CartActivity.this,"Success",jsonObject1.getString("msg"));
                                getCartListAndRefresh();
                            } else{
                                Utils.showCommonInfoPrompt(CartActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_PLACE_ORDER) {

                    if (response.isError()) {
                        Toast.makeText(CartActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray = null;
                            String status = jsonObject1.getString("status");
                            if (status.equalsIgnoreCase("true")) {
                                preference.setCART_COUNT(0);
                                Intent intent=new Intent(CartActivity.this,ThanksActivity.class);
                                startActivity(intent);
                            } else{
                                Utils.showCommonInfoPrompt(CartActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
    }

}