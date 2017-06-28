package com.sdj_jewellers;

import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdj_jewellers.adapter.GalleryListAdapter;
import com.sdj_jewellers.model.GalleryModel;
import com.sdj_jewellers.service.Response;
import com.sdj_jewellers.service.ResponseListener;
import com.sdj_jewellers.service.ServerRequest;
import com.sdj_jewellers.utility.Connection;
import com.sdj_jewellers.utility.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends BaseActivity implements ResponseListener{

    RecyclerView recyclerView;
    List<GalleryModel> list;
    GalleryListAdapter mAdapter;
    Handler h;
    ImageView toolbarBasket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
              getLayoutInflater().inflate(R.layout.activity_gallery,frameLayout);
 //       setContentView(R.layout.activity_gallery);
        h=new Handler();
        toolbar.setVisibility(View.VISIBLE);
        toolbarTitle.setText("Gallery");

        recyclerView = (RecyclerView) findViewById(R.id.gallery_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<GalleryModel>();

        mAdapter = new GalleryListAdapter(this, list);
        recyclerView.setAdapter(mAdapter);

        getAllCategories();
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

    public void getAllCategories() {

        if (Utils.ChechInternetAvalebleOrNot(GalleryActivity.this)) {

            Utils.showLoader(GalleryActivity.this);
            ServerRequest
                    .postRequest(
                            Connection.BASE_URL + "allcategory",
                            getAllCattegoryData(),
                            GalleryActivity.this,
                            ResponseListener.REQUEST_ALL_CATEGORY);

        } else {
            //   Utils.shonterwSnakeBar(layout_view, "internet not connected !!!", Color.RED);Toast.makeText(LoginActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            Toast.makeText(GalleryActivity.this,"Internet not connected !!!",Toast.LENGTH_LONG).show();
            return;
        }
}

    public JSONObject getAllCattegoryData() {
        JSONObject json = new JSONObject();
        try {

            json.put("", "");

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
                if (rid == ResponseListener.REQUEST_ALL_CATEGORY) {

                    if (response.isError()) {
                        Toast.makeText(GalleryActivity.this, response.getErrorMsg(),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.getData() != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response.getData());
                            JSONObject jsonObject = null;
                            JSONArray jsonArray=null;
                            String status=jsonObject1.getString("status");
                            if(status.equalsIgnoreCase("true")){
                                jsonArray=jsonObject1.getJSONArray("record");
                            }
                            GalleryModel model = null;
                            list.clear();

                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    jsonObject = jsonArray.getJSONObject(i);
                                    model = new GalleryModel();
                                    model.setTermId(jsonObject.getString("term_id"));
                                    model.setName(jsonObject.getString("name"));
                                    model.setProductCount(jsonObject.getString("product_count"));
                                    if(jsonObject.getString("image")!=null && !jsonObject.getString("image").equalsIgnoreCase("null"))
                                        model.setImageUrl(jsonObject.getString("image"));

                                    list.add(model);
                                }
                      //          getSupportActionBar().setTitle("Booking ("+booking_list.size()+")");
                      //          recyclerView.setAdapter(new GalleryListAdapter(GalleryActivity.this,list));
                                mAdapter.notifyDataSetChanged();
                            }else{
                                Toast.makeText(GalleryActivity.this,jsonObject1.getString("msg"),Toast.LENGTH_LONG).show();
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
