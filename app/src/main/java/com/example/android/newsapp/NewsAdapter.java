package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> newsList) {
        super(context, 0 ,newsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        News currentNews = getItem(position);

        TextView newsTitle = (TextView) convertView.findViewById(R.id.news_title);
        newsTitle.setText(currentNews.getTitle());

        TextView newsAuthor = (TextView) convertView.findViewById(R.id.author);
        newsAuthor.setText(currentNews.getAuthor());

        TextView newsTime = (TextView) convertView.findViewById(R.id.time);
        newsTime.setText(currentNews.getTime());

        return convertView;
    }
}
