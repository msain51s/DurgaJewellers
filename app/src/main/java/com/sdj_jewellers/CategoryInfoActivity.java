package com.sdj_jewellers;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdj_jewellers.adapter.CategoryInfoAdapter;
import com.sdj_jewellers.db.DB_Handler;
import com.sdj_jewellers.model.CategoryInfo;
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

public class CategoryInfoActivity extends BaseActivity implements ResponseListener{
    RecyclerView recyclerView;
    List<CategoryInfo> list;
    CategoryInfoAdapter mAdapter;
    Handler h;
    String catId,title,cartId;
    int listItemSelectedPosition=-1;
    ImageView toolbarBasket;

    DB_Handler db_handler;
    Preference preference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category_info);
        getLayoutInflater().inflate(R.layout.activity_category_info,frameLayout);
        h = new Handler();
        db_handler=new DB_Handler(this);
        preference=new Preference(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            catId=bundle.getString("catId");
            title=bundle.getString("title");
        }

        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText(title);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        toolbarBasket= (ImageView) findViewById(R.id.toolbar_cart_icon);
        recyclerView = (RecyclerView) findViewById(R.id.categoryInfo_recyclerview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<CategoryInfo>();

        mAdapter = new CategoryInfoAdapter(this, list,"CategoryInfo");
        recyclerView.setAdapter(mAdapter);


        getSelectedCategoryInfo();

        toolbarBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryInfoActivity.this,CartActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getSelectedCategoryInfo();
    }

    public void productAddToCart(int position, String quantity, int addOrUpdate){
    //    Toast.makeText(this,"quantity-position"+quantity+"-"+position,Toast.LENGTH_LONG).show();
       /* list.get(position).setProduct_quantity(Integer.parseInt(quantity));
        db_handler.addDataToCart(list.get(position));*/
        listItemSelectedPosition=position;
        if(addOrUpdate==0){
            addProductsToCart(list.get(position),quantity);
        }else if(addOrUpdate==1){
            updateCartProduct(list.get(position),quantity);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

            if(preference.getCART_COUNT()!=0) {
                cart_countText.setVisibility(View.VISIBLE);
                cart_countText.setText(""+preference.getCART_COUNT());
            }else
                cart_countText.setVisibility(View.GONE);

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

    public void onFilterClick(View view){
        Utils.showFilterPrompt(this,"FILTERS");
    }

    public void onSortClick(View view){
        Utils.showSortByPrompt(this,"SORT BY");
    }

  /*SUB CATEGORY CLICK*/
    public void onSubCategoryClick(View view){}

  /*CHECK CART PRODUCTS*/
    public void checkCartProduct(int position){
        listItemSelectedPosition=position;
        if (Utils.ChechInternetAvalebleOrNot(CategoryInfoActivity.this)) {

            Utils.showLoader(CategoryInfoActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "check_cartProducts",
                            getCheckCartProductData(preference.getUSER_ID(),list.get(position).getCatID()),
                            CategoryInfoActivity.this,
                            ResponseListener.REQUEST_CHECK_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CategoryInfoActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getCheckCartProductData(String userId,int postId) {
        JSONObject json = new JSONObject();
        try {

            json.put("userId", userId);
            json.put("postId", postId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    /*UPDATE PRODUCTS QUANTITY*/
    public void updateCartProduct(CategoryInfo model,String quantity){
        if (Utils.ChechInternetAvalebleOrNot(CategoryInfoActivity.this)) {

            Utils.showLoader(CategoryInfoActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "update_cartProducts",
                            getUpdateCartProductData(preference.getUSER_ID(),model.getCatID(),quantity),
                            CategoryInfoActivity.this,
                            ResponseListener.REQUEST_UPDATE_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CategoryInfoActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getUpdateCartProductData(String userId,int cartId,String qty) {
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

    /*ADD PRODUCTS TO CART*/
    public void addProductsToCart(CategoryInfo model,String quantity){
        if (Utils.ChechInternetAvalebleOrNot(CategoryInfoActivity.this)) {

            Utils.showLoader(CategoryInfoActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "save_addToCart",
                            getaddProductsToCartData(model,quantity),
                            CategoryInfoActivity.this,
                            ResponseListener.REQUEST_ADD_PRODUCT_TO_CART);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CategoryInfoActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }
    public JSONObject getaddProductsToCartData(CategoryInfo model,String qty) {
        JSONObject json = new JSONObject();
        try {

            json.put("postId", model.getCatID());
            json.put("postName", model.getPostTitle());
            json.put("postExcerpt", model.getPostExcerpt());
            json.put("userId", preference.getUSER_ID());
            json.put("quantity", qty);
            json.put("postimagePath", model.getImagePath());
            json.put("categoryID", catId);
            json.put("term_taxonomy_Id", model.getTermTaxonomyID());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }


    public void getSelectedCategoryInfo() {

        if (Utils.ChechInternetAvalebleOrNot(CategoryInfoActivity.this)) {

            Utils.showLoader(CategoryInfoActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "get_all_products",
                            getCattegoryInfoData(catId),
                            CategoryInfoActivity.this,
                            ResponseListener.REQUEST_CATEGORY_INFO);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(CategoryInfoActivity.this, "Internet not connected !!!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public JSONObject getCattegoryInfoData(String catId) {
        JSONObject json = new JSONObject();
        try {

            json.put("catID", catId);
            json.put("userId", preference.getUSER_ID());

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
                if (rid == ResponseListener.REQUEST_CATEGORY_INFO) {

                    if (response.isError()) {
                        Toast.makeText(CategoryInfoActivity.this, response.getErrorMsg(),
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
                                jsonArray = jsonObject1.getJSONArray("record");

                            CategoryInfo model = null;
                            list.clear();

                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new CategoryInfo();
                                    model.setCatID(jsonObject.getInt("ID"));
                                    model.setPostTitle(jsonObject.getString("post_title"));
                                    model.setPostDate(jsonObject.getString("post_date"));
                                    model.setPostContent(jsonObject.getString("post_content"));
                                    model.setPostExcerpt(jsonObject.getString("post_excerpt"));
                                    model.setPostStatus(jsonObject.getString("post_status"));
                                    model.setPostName(jsonObject.getString("post_name"));
                                    model.setPostModifiedDate(jsonObject.getString("post_modified"));
                                    model.setPostType(jsonObject.getString("post_type"));
                                    model.setImage(jsonObject.getString("imageKey"));
                                    model.setImagePath(jsonObject.getString("imagePath"));
                         //           model.setParent(jsonObject.getString("parent"));
                                    model.setTermTaxonomyID(jsonObject.getString("term_taxonomy_id"));

                                    list.add(model);
                                }
                                //          getSupportActionBar().setTitle("Booking ("+booking_list.size()+")");
                                //          recyclerView.setAdapter(new GalleryListAdapter(GalleryActivity.this,list));
                                mAdapter.notifyDataSetChanged();
                                if(jsonObject1.getInt("cartCount")!=0) {
                                    preference.setCART_COUNT(jsonObject1.getInt("cartCount"));
                                    cart_countText.setVisibility(View.VISIBLE);
                                    cart_countText.setText(""+jsonObject1.getInt("cartCount"));
                                }
                            }
                            } else {
                                Utils.showCommonInfoPrompt(CategoryInfoActivity.this,"Alert",jsonObject1.getString("msg"));
                                if(preference.getCART_COUNT()!=0) {
                                    cart_countText.setVisibility(View.VISIBLE);
                                    cart_countText.setText(""+preference.getCART_COUNT());
                                }
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_CHECK_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(CategoryInfoActivity.this, response.getErrorMsg(),
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
                                jsonObject = jsonObject1.getJSONObject("record");
                                if(jsonObject!=null) {
                                    cartId = jsonObject.getString("cart_id");
                                    Utils.showQuantityPrompt(CategoryInfoActivity.this, list.get(listItemSelectedPosition).getPostTitle(), listItemSelectedPosition, "Please Enter quantity to order", 1, jsonObject.getString("cart_quantity"),"CategoryInfo");
                                }
                            }
                            else if(status.equalsIgnoreCase("false") && jsonObject1.getString("msg").equalsIgnoreCase("No Record found")){
                                Utils.showQuantityPrompt(CategoryInfoActivity.this,list.get(listItemSelectedPosition).getPostTitle(),listItemSelectedPosition,"Please Enter quantity to order",0,"","CategoryInfo");
                            }else{
                                Toast.makeText(CategoryInfoActivity.this, jsonObject1.getString("msg"), Toast.LENGTH_LONG).show();
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_ADD_PRODUCT_TO_CART) {

                    if (response.isError()) {
                        Toast.makeText(CategoryInfoActivity.this, response.getErrorMsg(),
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
                                Utils.showCommonInfoPrompt(CategoryInfoActivity.this,"Success",jsonObject1.getString("msg"));
                                    preference.setCART_COUNT(preference.getCART_COUNT() + 1);
                                    cart_countText.setVisibility(View.VISIBLE);
                                    cart_countText.setText(""+preference.getCART_COUNT());
                            } else{
                                Utils.showCommonInfoPrompt(CategoryInfoActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_UPDATE_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(CategoryInfoActivity.this, response.getErrorMsg(),
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
                                Utils.showCommonInfoPrompt(CategoryInfoActivity.this,"Success",jsonObject1.getString("msg"));
                            } else{
                                Utils.showCommonInfoPrompt(CategoryInfoActivity.this,"Failed",jsonObject1.getString("msg"));
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