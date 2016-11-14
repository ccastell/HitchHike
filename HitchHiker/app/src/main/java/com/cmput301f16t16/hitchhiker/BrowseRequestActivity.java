package com.cmput301f16t16.hitchhiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by V1CTORIA2LEE on 2016-11-12.
 */
public class BrowseRequestActivity extends AppCompatActivity{

    private ListView theBrowseList;
    private ArrayList<Request> browseList = new ArrayList<Request>();
    private ArrayAdapter<Request> browseAdapter;
    private User user;

    /**
     * browseList is an array of all pending requests
     * request available. The list view is clickable to a new activity to view a
     * map of the requests.
     *
     * request object is passed to the
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_request);

        user = (User) getIntent().getSerializableExtra("user");

        // display requests into the listview
        theBrowseList = (ListView) findViewById(R.id.browsing_requests_listview);
        theBrowseList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                Intent intent = new Intent(BrowseRequestActivity.this, LocationViewActivity.class);
                Request chosenRequest = browseList.get(position);
                intent.putExtra("request", chosenRequest);
                startActivity(intent);
            }
        });


        try {
            browseList.clear();
            ElasticsearchRequestController.GetBrowsingRequestsTask getBrowsingRequestsTask = new ElasticsearchRequestController.GetBrowsingRequestsTask();
            getBrowsingRequestsTask.execute("");
            browseList = getBrowsingRequestsTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests out of the async object.");
        }
        browseAdapter = new ArrayAdapter<Request>(this, R.layout.request_list_item, browseList);
        theBrowseList.setAdapter(browseAdapter);
        browseAdapter.notifyDataSetChanged();
    }

    public void updateBrowseList(View view) {
        browseList.clear();
        try {
            ElasticsearchRequestController.GetBrowsingRequestsTask getBrowseRequestsTask = new ElasticsearchRequestController.GetBrowsingRequestsTask();
            getBrowseRequestsTask.execute("");
            browseList = getBrowseRequestsTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the requests out of the async object.");
        }
        browseAdapter = new ArrayAdapter<Request>(this, R.layout.request_list_item, browseList);
        theBrowseList.setAdapter(browseAdapter);
        browseAdapter.notifyDataSetChanged();
    }

    public void GoToDriverProfilePage(View view) {
        Intent intent = new Intent(BrowseRequestActivity.this, DriverActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}


