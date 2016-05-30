package com.prempal.crypt;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class MainActivity extends BaseActivity implements NetworkInterface {

    public static final String TAG = MainActivity.class.getSimpleName();
    private String currentPath = "/";
    private String GET_URL = BASE_URL + "list?dir=%s";
    private String DOWNLOAD_URL = BASE_URL + "%s?download=1";
    private RecyclerView mRecyclerView;
    private DirectoryAdapter mAdapter;
    private DownloadManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DirectoryAdapter(this, new JSONArray());
        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fetchDirectoryListing();

    }

    @Override
    public void fetchDirectoryListing() {
        showProgressDialog("Fetching..");
        String url = String.format(GET_URL, currentPath);
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dismissProgressDialog();
                Log.d(TAG, "onResponse: " + response);
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(request);
    }

    @Override
    public void downloadFile(String fileName) {
        String url = String.format(DOWNLOAD_URL, currentPath + fileName);
        Uri uri = Uri.parse(url);
        DownloadManager.Request dr = new DownloadManager.Request(uri);
        dm.enqueue(dr);
        Toast.makeText(MainActivity.this, "Downloading..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateCurrentPath(String folder) {
        currentPath = currentPath + folder + "/";
    }
}
