package com.example.nickelfoxassignment.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nickelfoxassignment.Dialogs.CustomDialogBox;
import com.example.nickelfoxassignment.Models.NewsModel;
import com.example.nickelfoxassignment.NewsDetailsActivity;
import com.example.nickelfoxassignment.R;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


//News Adapter holds channels and categories data...

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<NewsModel> list;
    Typeface MR, MRR;

    public NewsAdapter(Activity activity, Context context, List<NewsModel> list) {
        this.context = context;
        this.list = list;
        this.activity = activity;

        //Instantiating Typefaces...
        MRR = Typeface.createFromAsset(activity.getAssets(), "fonts/myriadregular.ttf");
        MR = Typeface.createFromAsset(activity.getAssets(), "fonts/myriad.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsModel newsModel = list.get(position);

        holder.news_title.setText(newsModel.getNews_title());
        holder.news_time.setText(newsModel.getNews_time());

        holder.news_title.setTypeface(MR);
        holder.news_time.setTypeface(MRR);

        if(newsModel.getNews_urlToImage()!= null && !newsModel.getNews_urlToImage().isEmpty()) {
            Picasso.with(context).load(newsModel.getNews_urlToImage()).placeholder(R.drawable.newspaper).into(holder.news_image);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowDetailsDialog(newsModel);
                /*Intent intent = new Intent(context, NewsDetailsActivity.class);
                Bundle b = new Bundle();
                b.putString("title", newsModel.getNews_title());
                b.putString("time" , newsModel.getNews_time());
                b.putString("urlToImage", newsModel.getNews_urlToImage());
                b.putString("author" , newsModel.getNews_author());
                b.putString("desc" , newsModel.getNews_desc());
                b.putString("content" , newsModel.getNews_content());
                b.putString("url" , newsModel.getNews_url());

                intent.putExtras(b);
                context.startActivity(intent);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView news_title, news_time;
        public CircleImageView news_image;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            news_title = itemView.findViewById(R.id.news_title);
            news_image = itemView.findViewById(R.id.news_image);
            news_time = itemView.findViewById(R.id.news_time);
            layout = itemView.findViewById(R.id.layout);
        }
    }

    //Creating CustomDialog with Fragment Transaction....
    private void ShowDetailsDialog(NewsModel newsModel) {
        //getActivity().getSupportFragmentManager()
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        CustomDialogBox customDetailsDialog = new CustomDialogBox();
        transaction.add(customDetailsDialog, "loading");
        Bundle b = new Bundle();
        b.putString("title", newsModel.getNews_title());
        b.putString("time" , newsModel.getNews_time());
        b.putString("urlToImage", newsModel.getNews_urlToImage());
        b.putString("author" , newsModel.getNews_author());
        b.putString("desc" , newsModel.getNews_desc());
        b.putString("content" , newsModel.getNews_content());
        b.putString("url" , newsModel.getNews_url());

        customDetailsDialog.setArguments(b);

        transaction.commitAllowingStateLoss();
    }




}