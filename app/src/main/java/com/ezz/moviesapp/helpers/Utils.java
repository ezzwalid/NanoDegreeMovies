package com.ezz.moviesapp.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class Utils {
    public static void loadImageWithProgress(ImageView imageView, final ProgressBar progressBar, String imagePath){
        String url = Contacts.IMAGE_HOST + imagePath;
        progressBar.setVisibility(View.VISIBLE);
        Picasso.with(imageView.getContext()).load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public static String parseDate(String date_string, String format)
    {
        String parsedDate= null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
        try {
            date = df.parse(date_string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        df = new SimpleDateFormat(format);
        parsedDate = df.format(date);
        return parsedDate;
    }

    public static void share(String link, String title, Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TITLE, title);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

}
