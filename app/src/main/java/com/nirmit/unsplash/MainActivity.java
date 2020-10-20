package com.nirmit.unsplash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;

    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page=1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView = findViewById(R.id.scrool_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        shimmerFrameLayout = findViewById(R.id.shimmer_layout);

        adapter = new MainAdapter(MainActivity.this, dataArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        getData();

        shimmerFrameLayout.startShimmer();

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData();
                }
            }
        });
    }

    private void getData() {

        String sUrl = "https://picsum.photos/v2/list?page="+page+"&limit="+limit;
        StringRequest request = new StringRequest(sUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response!=null){
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);

                    try {
                        //Initilaize json array
                        JSONArray jsonArray = new JSONArray(response);

                        //parse array
                        ParseArray(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        //Initilize request queue
        RequestQueue queue = Volley.newRequestQueue(this);
        //add request
        queue.add(request);

    }

    private void ParseArray(JSONArray jsonArray) {

        //use for loop
        Log.d("parsearray", "ParseArray: "+jsonArray);
        for(int i=0; i<jsonArray.length(); i++){


            try {

                //initialize json object
                JSONObject object = jsonArray.getJSONObject(i);
                //initialize main data
                MainData data = new MainData();
                //set image
                data.setImage(object.getString("download_url"));
                //set name
                data.setName(object.getString("author"));
                //set url
                data.setUrl(object.getString("url"));
                //add values in arraylist
                dataArrayList.add(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //initilaize adaptor
            adapter = new MainAdapter(MainActivity.this,dataArrayList);
            //set adapter
            recyclerView.setAdapter(adapter);
        }

    }
}