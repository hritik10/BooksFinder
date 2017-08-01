package com.example.android.booksfinder;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    private String mURL;

    public BookLoader(Context context, String URL) {
        super(context);
        mURL = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        List<Book> book = QueryUtils.fetchData(mURL);
        return book;
    }
}
