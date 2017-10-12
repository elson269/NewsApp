package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String LOG_TAG = NewsActivity.class.getName();
    private static final String NEWS_REQUEST_URL = "http://content.guardianapis.com/search?q=nougat&api-key=test&show-tags=contributor";
    private static final int NEWS_LOADER_ID = 1;
    private NewsAdapter newsAdapter;
    private TextView emptyTextView;
    private ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsListView = (ListView) findViewById(R.id.list);
        emptyTextView = (TextView) findViewById(R.id.empty_text);

        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);

        getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = newsAdapter.getItem(position);
                Intent newsListIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getUrl()));
                startActivity(newsListIntent);
            }
        });
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            return new NewsLoader(this, NEWS_REQUEST_URL);
        } else {
            Toast.makeText(NewsActivity.this, "Network unavailable.", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        newsAdapter.clear();
        if (newsList != null && !newsList.isEmpty()) {
            newsAdapter.addAll(newsList);
        } else {
            emptyTextView.setText("No results found.");
            newsListView.setEmptyView(emptyTextView);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }
}
