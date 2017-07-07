package com.sdj_jewellers;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdj_jewellers.adapter.CategoryInfoAdapter;
import com.sdj_jewellers.adapter.SearchAdapter;
import com.sdj_jewellers.model.CategoryInfo;
import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.FontType;
import com.sdj_jewellers.utility.Preference;
import com.sdj_jewellers.utility.Utils;
import com.search.material.library.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements ResponseListener {

    Toolbar toolbar;
    MaterialSearchView searchView;
    SearchAdapter adapter;
    TextView toolbarTitle;
    RecyclerView recyclerView;
    List<CategoryInfo> list;
    CategoryInfoAdapter mAdapter;
    int listItemSelectedPosition=-1;
    String cartId;
    int addOrUpdateGlobal;


    Preference preference;
    Handler handler;
    Typeface roboto_bold;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        preference=new Preference(this);
        handler=new Handler();
        roboto_bold = Utils.getCustomFont(this, FontType.ROBOTO_BOLD);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarTitle= (TextView) findViewById(R.id.toolbar_title_text);
        toolbarTitle.setTypeface(roboto_bold);
        toolbarTitle.setText("Search Product");

        recyclerView = (RecyclerView) findViewById(R.id.search_product_recyclerview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<CategoryInfo>();

        mAdapter = new CategoryInfoAdapter(this, list,"SearchProduct");
        recyclerView.setAdapter(mAdapter);

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.showSearch(true);

        adapter = new SearchAdapter(this);
        searchView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
            //    Toast.makeText(SearchActivity.this,query,Toast.LENGTH_LONG).show();
                getSearchProductList(query.trim());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

  // for voice search handling

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

  /*ADD PRODUCT TO CART*/

    public void productAddToCart(int position, String quantity, int addOrUpdate){

        listItemSelectedPosition=position;
        addOrUpdateGlobal=addOrUpdate;
       /* if(addOrUpdate==0){*/
            addProductsToCart(list.get(position),quantity);
        /*}else if(addOrUpdate==1){
            updateCartProduct(list.get(position),quantity);
        }*/
    }


    /*CHECK CART PRODUCTS*/
    public void checkCartProduct(int position){
        listItemSelectedPosition=position;
        if (Utils.ChechInternetAvalebleOrNot(SearchActivity.this)) {

            Utils.showLoader(SearchActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "check_cartProducts",
                            getCheckCartProductData(preference.getUSER_ID(),list.get(position).getCatID()),
                            SearchActivity.this,
                            ResponseListener.REQUEST_CHECK_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Utils.showCommonInfoPrompt(this,"Alert","Internet Not Connected !!! please try again later");
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
        if (Utils.ChechInternetAvalebleOrNot(SearchActivity.this)) {

            Utils.showLoader(SearchActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "update_cartProducts",
                            getUpdateCartProductData(preference.getUSER_ID(),model.getCatID(),quantity),
                            SearchActivity.this,
                            ResponseListener.REQUEST_UPDATE_CART_PRODUCTS);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Utils.showCommonInfoPrompt(this,"Alert","Internet Not Connected !!! please try again later");
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
        if (Utils.ChechInternetAvalebleOrNot(SearchActivity.this)) {

            Utils.showLoader(SearchActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "save_addToCart",
                            getaddProductsToCartData(model,quantity),
                            SearchActivity.this,
                            ResponseListener.REQUEST_ADD_PRODUCT_TO_CART);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Utils.showCommonInfoPrompt(this,"Alert","Internet Not Connected !!! please try again later");
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
            json.put("categoryID", model.getTermTaxonomyID());
            json.put("term_taxonomy_Id", model.getTermTaxonomyID());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    public void getSearchProductList(String searchText){
        if (Utils.ChechInternetAvalebleOrNot(SearchActivity.this)) {

            Utils.showLoader(SearchActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "get_search_products",
                            getSearchProductData(searchText),
                            SearchActivity.this,
                            ResponseListener.REQUEST_GET_SEARCH_PRODUCT_LIST);

        } else {
            //   Utils.showSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Utils.showCommonInfoPrompt(this,"Alert","Internet Not Connected !!! please try again later");
            return;
        }
    }

    public JSONObject getSearchProductData(String searchText) {
        JSONObject json = new JSONObject();
        try {
            json.put("searchTxt", searchText);
            json.put("userId",preference.getUSER_ID() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;

    }

    @Override
    public void onResponse(final Response response, final int rid) {


        handler.post(new Runnable() {

            @Override
            public void run() {
                Utils.dismissLoader();
                if (rid == ResponseListener.REQUEST_GET_SEARCH_PRODUCT_LIST) {

                    if (response.isError()) {
                        Toast.makeText(SearchActivity.this, response.getErrorMsg(),
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
                                        //        model.setParent(jsonObject.getString("parent"));
                                        model.setTermTaxonomyID(jsonObject.getString("term_taxonomy_id"));

                                        list.add(model);
                                    }
                                    //          getSupportActionBar().setTitle("Booking ("+booking_list.size()+")");
                                    //          recyclerView.setAdapter(new GalleryListAdapter(GalleryActivity.this,list));
                                    mAdapter.notifyDataSetChanged();
                                    if(jsonObject1.getInt("cartCount")!=0) {
                                        preference.setCART_COUNT(jsonObject1.getInt("cartCount"));
                                    }
                                }
                            } else {
                                //   Toast.makeText(CategoryInfoActivity.this, jsonObject1.getString("msg"), Toast.LENGTH_LONG).show();
                                Utils.showCommonInfoPrompt(SearchActivity.this,"Alert",jsonObject1.getString("msg"));

                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_CHECK_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(SearchActivity.this, response.getErrorMsg(),
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
                                    Utils.showQuantityPrompt(SearchActivity.this, list.get(listItemSelectedPosition).getPostTitle(), listItemSelectedPosition, "Please Enter quantity to order", 1, jsonObject.getString("cart_quantity"),"SearchProduct");
                                }
                            }
                            else if(status.equalsIgnoreCase("false") && jsonObject1.getString("msg").equalsIgnoreCase("No Record found")){
                                Utils.showQuantityPrompt(SearchActivity.this,list.get(listItemSelectedPosition).getPostTitle(),listItemSelectedPosition,"Please Enter quantity to order",0,"","SearchProduct");
                            }else{
                                Toast.makeText(SearchActivity.this, jsonObject1.getString("msg"), Toast.LENGTH_LONG).show();
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_ADD_PRODUCT_TO_CART) {

                    if (response.isError()) {
                        Toast.makeText(SearchActivity.this, response.getErrorMsg(),
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
                                Utils.showCommonInfoPrompt(SearchActivity.this,"Success",jsonObject1.getString("msg"));

                            if(addOrUpdateGlobal!=1)
                                preference.setCART_COUNT(preference.getCART_COUNT() + 1);

                            } else{
                                Utils.showCommonInfoPrompt(SearchActivity.this,"Failed",jsonObject1.getString("msg"));
                            }

                            Log.d("json_response", response.getData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }else if (rid == ResponseListener.REQUEST_UPDATE_CART_PRODUCTS) {

                    if (response.isError()) {
                        Toast.makeText(SearchActivity.this, response.getErrorMsg(),
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
                                Utils.showCommonInfoPrompt(SearchActivity.this,"Success",jsonObject1.getString("msg"));
                            } else{
                                Utils.showCommonInfoPrompt(SearchActivity.this,"Failed",jsonObject1.getString("msg"));
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
